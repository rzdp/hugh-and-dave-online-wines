package com.rzdp.winestoreapi.service.user.impl;

import com.rzdp.winestoreapi.config.properties.EmailProperties;
import com.rzdp.winestoreapi.config.properties.ImageUserProperties;
import com.rzdp.winestoreapi.config.properties.MessageProperties;
import com.rzdp.winestoreapi.constant.UserRole;
import com.rzdp.winestoreapi.dto.MailDto;
import com.rzdp.winestoreapi.dto.UserDto;
import com.rzdp.winestoreapi.dto.request.SignInRequest;
import com.rzdp.winestoreapi.dto.request.SignUpRequest;
import com.rzdp.winestoreapi.dto.response.MessageResponse;
import com.rzdp.winestoreapi.dto.response.SignInResponse;
import com.rzdp.winestoreapi.entity.Account;
import com.rzdp.winestoreapi.entity.User;
import com.rzdp.winestoreapi.exception.AccountAlreadyExistException;
import com.rzdp.winestoreapi.exception.AccountAlreadyVerifiedException;
import com.rzdp.winestoreapi.exception.UserUpdatePhotoException;
import com.rzdp.winestoreapi.mapper.RegisterRequestToUserMapper;
import com.rzdp.winestoreapi.mapper.UserToUserDtoMapper;
import com.rzdp.winestoreapi.mapper.UsertoLoginResponseMapper;
import com.rzdp.winestoreapi.security.JwtProvider;
import com.rzdp.winestoreapi.service.account.ExistsAccountByEmail;
import com.rzdp.winestoreapi.service.email.EmailService;
import com.rzdp.winestoreapi.service.role.GetRoleByName;
import com.rzdp.winestoreapi.service.ssh.SshService;
import com.rzdp.winestoreapi.service.user.CreateUser;
import com.rzdp.winestoreapi.service.user.GetUserById;
import com.rzdp.winestoreapi.service.user.UserService;
import com.rzdp.winestoreapi.service.userimage.UserImageService;
import com.rzdp.winestoreapi.utils.FileUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    // Single Service
    private final GetUserById getUserById;
    private final GetRoleByName getRoleByName;
    private final CreateUser createUser;
    private final ExistsAccountByEmail existsAccountByEmail;

    // Multiple Service
    private final UserImageService userImageService;
    private final SshService sshService;
    private final EmailService emailService;

    // Properties
    private final ImageUserProperties imageUserProperties;
    private final EmailProperties emailProperties;
    private final MessageProperties messageProperties;

    // Misc
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper mapper;

    @Autowired
    public UserServiceImpl(GetUserById getUserById,
                           GetRoleByName getRoleByName,
                           CreateUser createUser,
                           ExistsAccountByEmail existsAccountByEmail,
                           UserImageService userImageService,
                           SshService sshService,
                           EmailService emailService,
                           ImageUserProperties imageUserProperties,
                           EmailProperties emailProperties,
                           MessageProperties messageProperties,
                           AuthenticationManager authenticationManager,
                           JwtProvider jwtProvider,
                           PasswordEncoder passwordEncoder,
                           ModelMapper mapper) {
        this.getUserById = getUserById;
        this.getRoleByName = getRoleByName;
        this.createUser = createUser;
        this.existsAccountByEmail = existsAccountByEmail;
        this.userImageService = userImageService;
        this.sshService = sshService;
        this.emailService = emailService;
        this.imageUserProperties = imageUserProperties;
        this.emailProperties = emailProperties;
        this.messageProperties = messageProperties;
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
        this.passwordEncoder = passwordEncoder;
        this.mapper = mapper;
    }


    @Override
    @Transactional
    public UserDto getUserById(long userId) {
        log.info("Getting user by id: [{}]", userId);
        User user = getUserById.run(userId);
        mapper.addConverter(new UserToUserDtoMapper());
        log.info("Getting user successful. Responding with user details.");
        return mapper.map(user, UserDto.class);
    }

    @Override
    @Transactional
    public SignInResponse signIn(SignInRequest request) {
        // Log in user
        String requestUsername = request.getUsername();
        log.info("Logging in user: [{}]", requestUsername);
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        requestUsername,
                        request.getPassword()
                )
        );
        log.info("User [{}] successfully logged in!", requestUsername);

        // Set authentication to security context
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Generate jwt
        String jwt = jwtProvider.generateJwt(authentication);

        // Get user details
        log.info("Getting user details from token");
        Long userId = jwtProvider.getUserIdFromJwt(jwt);
        log.debug("Fetched [{}] user id from token", userId);


        log.info("Getting user by id [{}]", userId);
        User user = getUserById.run(userId);

        // Return jwt along with user details
        log.info("Getting user successful. Responding jwt along with user details.");
        mapper.addConverter(new UsertoLoginResponseMapper());
        SignInResponse response = mapper.map(user, SignInResponse.class);
        response.setAccessToken(jwt);
        return response;
    }

    @Override
    @Transactional
    public MessageResponse signUp(SignUpRequest request) {

        // Validate username
        String email = request.getEmail();

        if (existsAccountByEmail.run(email)) {
            throw new AccountAlreadyExistException(messageProperties
                    .getException().getAccount().getAlreadyExist());
        }
        // Map request to user entity
        mapper.addConverter(new RegisterRequestToUserMapper());
        User user = mapper.map(request, User.class);
        log.info("Creating user record for {}", user.getFullName());

        // Hash password
        Account account = user.getAccount();
        account.setPassword(passwordEncoder.encode(account.getPassword()));

        // Assign user role
        account.setRole(getRoleByName.run(UserRole.ROLE_USER));

        // Save the user
        createUser.run(user);
        log.info("User {} created successfully", user.getFullName());
        long userId = user.getUserId();

        log.info("Sending user verification via email to user");
        MailDto mailDto = new MailDto();
        mailDto.setSender(emailProperties.getSender());
        mailDto.setSubject(emailProperties.getRegistrationVerification().getSubject());
        mailDto.setReceiver(account.getEmail());

        Map<String, Object> props = new HashMap<>();
        props.put("firstName", user.getFirstName());
        props.put("url", emailProperties.getRegistrationVerification().getUrl() + userId);
        mailDto.setProps(props);

        emailService.sendUserVerificationEmail(mailDto);
        log.info("User verification email sent!");


        return new MessageResponse(messageProperties.getSuccess().getRegister());
    }

    @Override
    @Transactional
    public MessageResponse verifySignUp(long userId) {
        log.info("Verifying user account with id {}", userId);
        User user = getUserById.run(userId);
        Account account = user.getAccount();
        if (user.isActive() || account.isVerified()) {
            throw new AccountAlreadyVerifiedException(messageProperties
                    .getException().getAccount().getAlreadyVerified());
        }
        user.setActive(true);
        account.setVerified(true);
        user.setAccount(account);
        createUser.run(user);
        log.info("User account of {} verified successfully!", user.getFullName());
        return new MessageResponse(messageProperties.getSuccess().getVerifyUser());
    }

    @Override
    @Transactional
    public MessageResponse updateUserPhoto(long userId, MultipartFile file) {

        log.info("Updating photo of user with ID: {}", userId);
        // Get user by id
        User user = getUserById.run(userId);
        log.debug("User fetched: {}", user);

        // Update images
        int smallSize = imageUserProperties.getSmall();
        int mediumSize = imageUserProperties.getMedium();
        int largeSize = imageUserProperties.getLarge();

        File small, medium, large;
        try {
            byte[] data = file.getBytes();
            small = userImageService.createImageFile(userId, data, smallSize);
            medium = userImageService.createImageFile(userId, data, mediumSize);
            large = userImageService.createImageFile(userId, data, largeSize);
        } catch (IOException e) {
            throw new UserUpdatePhotoException(messageProperties.getException()
                    .getUser().getUpdatePhoto() + e.getMessage());
        }

        // Upload images to sftp
        List<String> imagePaths = new ArrayList<>();
        String remotePath = imageUserProperties.getRemotePath();
        imagePaths.add(sshService.uploadFile(small, remotePath +
                userImageService.getFilepathSize(smallSize).toUpperCase() + "/" + small.getName()));
        imagePaths.add(sshService.uploadFile(medium, remotePath +
                userImageService.getFilepathSize(mediumSize).toUpperCase() + "/" + medium.getName()));
        imagePaths.add(sshService.uploadFile(large, remotePath +
                userImageService.getFilepathSize(largeSize).toUpperCase() + "/" + large.getName()));

        // Set the path the the user entity
        imagePaths.forEach(imagePath -> userImageService.createImage(user, imagePath));


        // Transfer local file from created to uploaded
        FileUtils.transferFile(small.getAbsolutePath(), imageUserProperties.getProcessedPath());
        FileUtils.transferFile(medium.getAbsolutePath(), imageUserProperties.getProcessedPath());
        FileUtils.transferFile(large.getAbsolutePath(), imageUserProperties.getProcessedPath());

        log.info("User photo of {} updated successfully.", user.getFullName());

        return new MessageResponse(messageProperties.getSuccess().getUpdatePhoto());
    }
}

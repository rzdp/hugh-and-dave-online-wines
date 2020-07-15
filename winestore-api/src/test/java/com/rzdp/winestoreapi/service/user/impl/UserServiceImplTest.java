package com.rzdp.winestoreapi.service.user.impl;

import com.rzdp.winestoreapi.config.properties.EmailProperties;
import com.rzdp.winestoreapi.config.properties.MessageProperties;
import com.rzdp.winestoreapi.config.properties.MessageProperties.ExceptionMessage.ConfirmSignUp;
import com.rzdp.winestoreapi.constant.UserRole;
import com.rzdp.winestoreapi.dto.MailDto;
import com.rzdp.winestoreapi.dto.UserDto;
import com.rzdp.winestoreapi.dto.request.ConfirmSignUpRequest;
import com.rzdp.winestoreapi.dto.request.SignInRequest;
import com.rzdp.winestoreapi.dto.request.SignUpRequest;
import com.rzdp.winestoreapi.dto.response.MessageResponse;
import com.rzdp.winestoreapi.dto.response.SignInResponse;
import com.rzdp.winestoreapi.entity.Account;
import com.rzdp.winestoreapi.entity.Role;
import com.rzdp.winestoreapi.entity.User;
import com.rzdp.winestoreapi.entity.UserCode;
import com.rzdp.winestoreapi.exception.AccountAlreadyExistException;
import com.rzdp.winestoreapi.exception.AccountAlreadyVerifiedException;
import com.rzdp.winestoreapi.exception.ConfirmSignUpException;
import com.rzdp.winestoreapi.exception.EmailException;
import com.rzdp.winestoreapi.security.JwtProvider;
import com.rzdp.winestoreapi.service.account.ExistsAccountByEmail;
import com.rzdp.winestoreapi.service.email.EmailService;
import com.rzdp.winestoreapi.service.role.GetRoleByName;
import com.rzdp.winestoreapi.service.user.CreateUser;
import com.rzdp.winestoreapi.service.user.GetUserById;
import com.rzdp.winestoreapi.util.TestUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static com.rzdp.winestoreapi.config.properties.MessageProperties.ExceptionMessage;
import static com.rzdp.winestoreapi.config.properties.MessageProperties.ExceptionMessage.AlreadyExist;
import static com.rzdp.winestoreapi.config.properties.MessageProperties.ExceptionMessage.AlreadyVerified;
import static com.rzdp.winestoreapi.config.properties.MessageProperties.ExceptionMessage.UserVerification;
import static com.rzdp.winestoreapi.config.properties.MessageProperties.SuccessMessage;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@DisplayName("User Service Tests")
@TestMethodOrder(MethodOrderer.Alphanumeric.class)
class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private GetUserById getUserByIdMock;

    @Mock
    private ExistsAccountByEmail existsAccountByEmailMock;

    @Mock
    private GetRoleByName getRoleByNameMock;

    @Mock
    private CreateUser createUserMock;

    @Mock
    private AuthenticationManager authenticationManagerMock;

    @Mock
    private JwtProvider jwtProviderMock;

    @Mock
    private EmailService emailServiceMock;

    @Mock
    private PasswordEncoder passwordEncoderMock;

    @Mock
    private ModelMapper modelMapperMock;

    @Mock
    private SignInResponse signInResponseMock;

    @Mock
    private EmailProperties emailPropertiesMock;

    @Mock
    private EmailProperties.RegistrationVerification registrationVerificationMock;

    @Mock
    private MessageProperties messagePropertiesMock;

    @Mock
    private SuccessMessage successMessagePropertiesMock;

    @Mock
    private ExceptionMessage exceptionMessagePropertiesMock;

    @Mock
    private UserVerification userVerificationMessagePropertiesMock;

    @Mock
    private AlreadyExist alreadyExistMessagePropertiesMock;

    @Mock
    private AlreadyVerified alreadyVerifiedMessagePropertiesMock;

    @Mock
    private ConfirmSignUp confirmSignUpMessagePropertiesMock;

    @Test
    @DisplayName("getUserById() returns UserDto when successful")
    void getUserById_ReturnsUserDto_WhenSuccessful() {
        // Arrange
        User user = TestUtil.getUserData();
        long userId = user.getUserId();
        when(getUserByIdMock.run(userId))
                .thenReturn(user);
        UserDto expectedResult = TestUtil.getUserDto();
        when(modelMapperMock.map(user, UserDto.class))
                .thenReturn(expectedResult);

        // Act
        UserDto userDto = userService.getUserById(userId);

        // Assert
        assertThat(userDto).isNotNull();
        assertThat(userDto).isEqualTo(expectedResult);
        assertThat(userDto.getUserId()).isEqualTo(expectedResult.getUserId());
    }

    @Test
    @DisplayName("signIn() returns SignInResponse when credentials are valid")
    void signIn_ReturnsSignInResponse_WhenCredentialsAreValid() {
        // Arrange - Create Request
        User user = TestUtil.getUserData();
        String email = user.getAccount().getEmail();
        String password = user.getAccount().getPassword();

        // Arrange - Log in user
        Authentication authentication =
                new UsernamePasswordAuthenticationToken(email, password);
        when(authenticationManagerMock
                .authenticate(new UsernamePasswordAuthenticationToken(email, password)))
                .thenReturn(authentication);

        // Arrange - Generate jwt
        String expectedJwt = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9" +
                ".eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ" +
                ".SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";
        when(jwtProviderMock.generateJwt(authentication))
                .thenReturn(expectedJwt);

        // Arrange - Get user details
        long userId = user.getUserId();
        when(jwtProviderMock.getUserIdFromJwt(expectedJwt))
                .thenReturn(userId);
        when(getUserByIdMock.run(userId))
                .thenReturn(user);

        // Arrange - Return jwt along with user details
        doNothing().when(signInResponseMock).setAccessToken(expectedJwt);
        SignInResponse expectedResult = TestUtil.getSignInResponse();
        when(modelMapperMock.map(user, SignInResponse.class))
                .thenReturn(expectedResult);

        // Act
        SignInRequest request = new SignInRequest();
        request.setUsername(email);
        request.setPassword(password);
        SignInResponse signInResponse = userService.signIn(request);

        // Assert
        assertThat(signInResponse).isNotNull();
        assertThat(signInResponse).isEqualTo(expectedResult);
        assertThat(signInResponse.getAccessToken()).isNotNull();
        assertThat(signInResponse.getAccessToken()).isEqualTo(expectedJwt);
        assertThat(signInResponse.getEmail()).isNotNull();
        assertThat(signInResponse.getEmail()).isEqualTo(email);
        assertThat(SecurityContextHolder.getContext().getAuthentication()).isNotNull();
        assertThat(SecurityContextHolder.getContext().getAuthentication()).isEqualTo(authentication);
    }

    @Test
    @DisplayName("signIn() throws BadCredentialsException when credentials are invalid")
    void signIn_ThrowsBadCredentialsException_WhenCredentialsAreInvalid() {
        // Arrange - Create Request
        String email = "dummy@domain.com";
        String password = "password";

        // Arrange - Log in user
        when(authenticationManagerMock
                .authenticate(new UsernamePasswordAuthenticationToken(email, password)))
                .thenThrow(BadCredentialsException.class);

        // Act & Assert
        SignInRequest request = new SignInRequest();
        request.setUsername(email);
        request.setPassword(password);
        assertThat(SecurityContextHolder.getContext().getAuthentication()).isNull();
        assertThatExceptionOfType(BadCredentialsException.class)
                .isThrownBy(() -> userService.signIn(request));
    }

    @Test
    @DisplayName("signUp() returns MessageResponse when successful")
    void signUp_ReturnsMessageResponse_WhenSuccessful() {
        // Arrange - Create request
        SignUpRequest request = new SignUpRequest();
        String email = request.getEmail();

        // Arrange - Validate username
        when(existsAccountByEmailMock.run(email))
                .thenReturn(false);

        // Arrange - Map request to user entity
        User user = TestUtil.getUserData();
        when(modelMapperMock.map(request, User.class))
                .thenReturn(user);

        // Arrange - Hash password
        Account account = user.getAccount();
        String hashedPassword = "asdjoaijeioqj23lkadm#cxvcxv";
        when(passwordEncoderMock.encode(account.getPassword()))
                .thenReturn(hashedPassword);

        // Arrange - Assign user role
        Role role = account.getRole();
        when(getRoleByNameMock.run(UserRole.ROLE_USER))
                .thenReturn(role);

        // Arrange - Save the user
        when(createUserMock.run(user))
                .thenReturn(user);

        // Arrange - Send user verification email
        when(emailPropertiesMock.getSender())
                .thenReturn("no-reply@hughanddave.com");

        when(emailPropertiesMock.getRegistrationVerification())
                .thenReturn(registrationVerificationMock);

        when(registrationVerificationMock.getSubject())
                .thenReturn("Hugh and Dave Online Wines - Verify Your Account");

        doNothing().when(emailServiceMock).sendUserVerificationEmail(any(User.class), anyString());

        // Arrange - Return response
        when(messagePropertiesMock.getSuccess())
                .thenReturn(successMessagePropertiesMock);

        String expectedResult = "User account registered successfully";
        when(successMessagePropertiesMock.getRegister())
                .thenReturn(expectedResult);

        // Act
        MessageResponse response = userService.signUp(request);

        // Assert
        assertThat(response).isNotNull();
        assertThat(response.getMessage()).isNotNull();
        assertThat(response.getMessage()).isEqualTo(expectedResult);
    }


    @Test
    @DisplayName("signUp() throws AccountAlreadyExist when email is already used")
    void signUp_ThrowsAccountAlreadyExist_WhenEmailIsAlreadyUsed() {
        // Arrange - Create request
        SignUpRequest request = new SignUpRequest();
        String email = request.getEmail();

        // Arrange - Validate username
        when(existsAccountByEmailMock.run(email))
                .thenReturn(true);
        when(messagePropertiesMock.getException())
                .thenReturn(exceptionMessagePropertiesMock);
        when(exceptionMessagePropertiesMock.getAlreadyExist())
                .thenReturn(alreadyExistMessagePropertiesMock);

        String expectedMessage = "Username already used";
        when(alreadyExistMessagePropertiesMock.getAccount())
                .thenReturn(expectedMessage);

        // Act & Assert
        assertThatExceptionOfType(AccountAlreadyExistException.class)
                .isThrownBy(() -> userService.signUp(request))
                .withMessage(expectedMessage);
    }

    @Test
    @DisplayName("signUp() hashed the account password when saving to the database")
    void signUp_ThrowsAccountAlreadyExist_WhenSavingToTheDatabase() {
        // Arrange - Create request
        SignUpRequest request = new SignUpRequest();
        String email = request.getEmail();

        // Arrange - Validate username
        when(existsAccountByEmailMock.run(email))
                .thenReturn(false);

        // Arrange - Map request to user entity
        User user = TestUtil.getUserData();
        when(modelMapperMock.map(request, User.class))
                .thenReturn(user);

        // Arrange - Hash password
        Account account = user.getAccount();
        String password = account.getPassword();
        String hashedPassword = "asdjoaijeioqj23lkadm#cxvcxv";
        when(passwordEncoderMock.encode(account.getPassword()))
                .thenReturn(hashedPassword);

        // Arrange - Assign user role
        Role role = account.getRole();
        when(getRoleByNameMock.run(UserRole.ROLE_USER))
                .thenReturn(role);

        // Arrange - Save the user
        when(createUserMock.run(user))
                .thenReturn(user);

        // Arrange - Send user verification email
        when(emailPropertiesMock.getSender())
                .thenReturn("no-reply@hughanddave.com");

        when(emailPropertiesMock.getRegistrationVerification())
                .thenReturn(registrationVerificationMock);

        when(registrationVerificationMock.getSubject())
                .thenReturn("Hugh and Dave Online Wines - Verify Your Account");

        doNothing().when(emailServiceMock).sendUserVerificationEmail(any(User.class), anyString());

        // Arrange - Return response
        when(messagePropertiesMock.getSuccess())
                .thenReturn(successMessagePropertiesMock);

        String expectedResult = "User account registered successfully";
        when(successMessagePropertiesMock.getRegister())
                .thenReturn(expectedResult);

        // Act
        userService.signUp(request);

        // Assert
        assertThat(user.getAccount().getPassword()).isNotNull();
        assertThat(user.getAccount().getPassword()).isNotEqualTo(password);
        assertThat(user.getAccount().getPassword()).isEqualTo(hashedPassword);
    }

    @Test
    @DisplayName("signUp() assigned USER role when account is registered")
    void signUp_AssignedUserRole_WhenAcountIsRegistered() {
        // Arrange - Create request
        SignUpRequest request = new SignUpRequest();
        String email = request.getEmail();

        // Arrange - Validate username
        when(existsAccountByEmailMock.run(email))
                .thenReturn(false);

        // Arrange - Map request to user entity
        User user = TestUtil.getUserData();
        when(modelMapperMock.map(request, User.class))
                .thenReturn(user);

        // Arrange - Hash password
        Account account = user.getAccount();
        String hashedPassword = "asdjoaijeioqj23lkadm#cxvcxv";
        when(passwordEncoderMock.encode(account.getPassword()))
                .thenReturn(hashedPassword);

        // Arrange - Assign user role
        String role = UserRole.ROLE_USER;
        Role expectedRole = account.getRole();
        when(getRoleByNameMock.run(role))
                .thenReturn(expectedRole);

        // Arrange - Save the user
        when(createUserMock.run(user))
                .thenReturn(user);

        // Arrange - Send user verification email
        when(emailPropertiesMock.getSender())
                .thenReturn("no-reply@hughanddave.com");

        when(emailPropertiesMock.getRegistrationVerification())
                .thenReturn(registrationVerificationMock);

        when(registrationVerificationMock.getSubject())
                .thenReturn("Hugh and Dave Online Wines - Verify Your Account");

        doNothing().when(emailServiceMock).sendUserVerificationEmail(any(User.class), anyString());

        when(messagePropertiesMock.getException())
                .thenReturn(exceptionMessagePropertiesMock);

        when(exceptionMessagePropertiesMock.getUserVerification())
                .thenReturn(userVerificationMessagePropertiesMock);

        when(userVerificationMessagePropertiesMock.getEmail())
                .thenReturn("Unable to send user verification email");

        // Arrange - Return response
        when(messagePropertiesMock.getSuccess())
                .thenReturn(successMessagePropertiesMock);

        String expectedResult = "User account registered successfully";
        when(successMessagePropertiesMock.getRegister())
                .thenReturn(expectedResult);

        // Act
        userService.signUp(request);

        // Assert
        assertThat(user.getAccount().getRole()).isNotNull();
        assertThat(user.getAccount().getRole().getName()).isNotEqualTo(UserRole.ROLE_ADMIN);
        assertThat(user.getAccount().getRole().getName()).isEqualTo(UserRole.ROLE_USER);
    }

    @Test
    @DisplayName("signUp() gets success response from email service when email was sent")
    void signUp_GetsSuccessResponseFromEmailService_WhenEmailWasSent() {
        // Arrange - Create request
        SignUpRequest request = new SignUpRequest();
        String email = request.getEmail();

        // Arrange - Validate username
        when(existsAccountByEmailMock.run(email))
                .thenReturn(false);

        // Arrange - Map request to user entity
        User user = TestUtil.getUserData();
        when(modelMapperMock.map(request, User.class))
                .thenReturn(user);

        // Arrange - Hash password
        Account account = user.getAccount();
        String hashedPassword = "asdjoaijeioqj23lkadm#cxvcxv";
        when(passwordEncoderMock.encode(account.getPassword()))
                .thenReturn(hashedPassword);

        // Arrange - Assign user role
        String role = UserRole.ROLE_USER;
        Role expectedRole = account.getRole();
        when(getRoleByNameMock.run(role))
                .thenReturn(expectedRole);

        // Arrange - Save the user
        when(createUserMock.run(user))
                .thenReturn(user);

        // Arrange - Send user verification email
        when(emailPropertiesMock.getSender())
                .thenReturn("no-reply@hughanddave.com");

        when(emailPropertiesMock.getRegistrationVerification())
                .thenReturn(registrationVerificationMock);

        when(registrationVerificationMock.getSubject())
                .thenReturn("Hugh and Dave Online Wines - Verify Your Account");

        doNothing().when(emailServiceMock).sendUserVerificationEmail(any(User.class), anyString());

        // Arrange - Return response
        when(messagePropertiesMock.getSuccess())
                .thenReturn(successMessagePropertiesMock);

        String expectedResult = "User account registered successfully";
        when(successMessagePropertiesMock.getRegister())
                .thenReturn(expectedResult);


        // Act & Assert
        assertThatCode(() -> userService.signUp(request))
                .doesNotThrowAnyException();
    }


    @Test
    @DisplayName("confirmSignUp() returns MessageResponse when successful")
    void confirmSignUp_ReturnsMessageResponse_WhenSuccessful() {
        User user = TestUtil.getUserData();
        long userId = user.getUserId();
        user.setActive(false);
        user.getAccount().setVerified(false);

        when(getUserByIdMock.run(userId))
                .thenReturn(user);

        when(createUserMock.run(user))
                .thenReturn(user);

        when(messagePropertiesMock.getSuccess())
                .thenReturn(successMessagePropertiesMock);

        String expectedMessage = "User verified successfully";
        when(successMessagePropertiesMock.getVerifySignUp())
                .thenReturn(expectedMessage);


        // Act & Assert
        ConfirmSignUpRequest request = new ConfirmSignUpRequest();
        request.setCode("111111");

        when(messagePropertiesMock.getException())
                .thenReturn(exceptionMessagePropertiesMock);

        when(exceptionMessagePropertiesMock.getConfirmSignUp())
                .thenReturn(confirmSignUpMessagePropertiesMock);

        when(confirmSignUpMessagePropertiesMock.getInvalidCode())
                .thenReturn("Invalid code");

        MessageResponse response = userService.confirmSignUp(userId, request);

        assertThat(response).isNotNull();
        assertThat(response).isInstanceOf(MessageResponse.class);
        assertThat(response.getMessage()).isEqualTo(expectedMessage);
    }

    @Test
    @DisplayName("confirmSignUp() throws AlreadyVerifiedException when email is active or verified")
    void confirmSignUp_ThrowsAlreadyVerifiedException_WhenEmailIsActiveOrVerified() {
        User user = TestUtil.getUserData();
        long userId = user.getUserId();

        when(getUserByIdMock.run(userId))
                .thenReturn(user);

        when(createUserMock.run(user))
                .thenReturn(user);

        when(messagePropertiesMock.getException())
                .thenReturn(exceptionMessagePropertiesMock);

        when(exceptionMessagePropertiesMock.getAlreadyVerified())
                .thenReturn(alreadyVerifiedMessagePropertiesMock);

        when(alreadyExistMessagePropertiesMock.getAccount())
                .thenReturn("User account already active or verified");

        // Act & Assert
        ConfirmSignUpRequest request = new ConfirmSignUpRequest();
        request.setCode("111111");
        assertThatExceptionOfType(AccountAlreadyVerifiedException.class)
                .isThrownBy(() -> userService.confirmSignUp(userId, request));
    }

    @Test
    @DisplayName("confirmSignUp() throws ConfirmSignUpException when no active sign up request")
    void confirmSignUp_ThrowsAlreadyVerifiedException_WhenNoActiveSignUpRequest() {
        User user = TestUtil.getUserData();
        user.setActive(false);
        user.getAccount().setVerified(false);
        long userId = user.getUserId();

        when(getUserByIdMock.run(userId))
                .thenReturn(user);

        when(createUserMock.run(user))
                .thenReturn(user);

        when(messagePropertiesMock.getException())
                .thenReturn(exceptionMessagePropertiesMock);

        when(exceptionMessagePropertiesMock.getConfirmSignUp())
                .thenReturn(confirmSignUpMessagePropertiesMock);

        when(confirmSignUpMessagePropertiesMock.getInvalidCode())
                .thenReturn("Invalid code");

        // Act & Assert
        ConfirmSignUpRequest request = new ConfirmSignUpRequest();
        request.setCode("000000");
        assertThatExceptionOfType(ConfirmSignUpException.class)
                .isThrownBy(() -> userService.confirmSignUp(userId, request));
    }

}
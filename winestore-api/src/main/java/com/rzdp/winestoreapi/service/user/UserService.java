package com.rzdp.winestoreapi.service.user;

import com.rzdp.winestoreapi.dto.UserDto;
import com.rzdp.winestoreapi.dto.request.SignInRequest;
import com.rzdp.winestoreapi.dto.request.SignUpRequest;
import com.rzdp.winestoreapi.dto.response.MessageResponse;
import com.rzdp.winestoreapi.dto.response.SignInResponse;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {

    UserDto getUserById(long userId);

    SignInResponse signIn(SignInRequest request);

    MessageResponse signUp(SignUpRequest request);

    MessageResponse notifySignUp(long userId);

    MessageResponse confirmSignUp(long userId);

    MessageResponse updateUserPhoto(long userId, MultipartFile file);

}

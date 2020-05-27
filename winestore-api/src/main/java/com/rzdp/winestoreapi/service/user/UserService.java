package com.rzdp.winestoreapi.service.user;

import com.rzdp.winestoreapi.dto.UserDto;
import com.rzdp.winestoreapi.dto.request.SignInRequest;
import com.rzdp.winestoreapi.dto.request.SignUpRequest;
import com.rzdp.winestoreapi.dto.response.SignInResponse;
import com.rzdp.winestoreapi.dto.response.MessageResponse;

public interface UserService {

    UserDto getUserById(long userId);

    SignInResponse signIn(SignInRequest request);

    MessageResponse signUp(SignUpRequest request);

    MessageResponse verifySignUp(long userId);
}

package com.rzdp.winestoreapi.controller;

import com.rzdp.winestoreapi.dto.request.ConfirmSignUpRequest;
import com.rzdp.winestoreapi.dto.request.SignInRequest;
import com.rzdp.winestoreapi.dto.request.SignUpRequest;
import com.rzdp.winestoreapi.dto.response.MessageResponse;
import com.rzdp.winestoreapi.dto.response.SignInResponse;
import com.rzdp.winestoreapi.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/v1/sign-in")
    public ResponseEntity<SignInResponse> signIn(@Valid @RequestBody SignInRequest request) {
        return new ResponseEntity<>(userService.signIn(request), HttpStatus.OK);
    }

    @PostMapping("/v1/sign-up")
    public ResponseEntity<MessageResponse> signUp(@Valid @RequestBody SignUpRequest request) {
        return new ResponseEntity<>(userService.signUp(request), HttpStatus.OK);
    }

    @PostMapping("/v1/sign-up/{userId}/notification")
    public ResponseEntity<MessageResponse> notifySignUp(@Valid @PathVariable long userId) {
        return new ResponseEntity<>(userService.notifySignUp(userId), HttpStatus.OK);
    }

    @PostMapping("/v1/sign-up/{userId}/confirmation")
    public ResponseEntity<MessageResponse> confirmSignUp(@Valid @PathVariable long userId,
                                                         @RequestBody ConfirmSignUpRequest request) {
        return new ResponseEntity<>(userService.confirmSignUp(userId, request), HttpStatus.OK);
    }

}

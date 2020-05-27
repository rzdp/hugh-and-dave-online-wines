package com.rzdp.winestoreapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class UserUpdatePhotoException extends RuntimeException {

    public UserUpdatePhotoException(String message) {
        super(message);
    }
}

package com.rzdp.winestoreapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ConfirmSignUpException extends RuntimeException {

    public ConfirmSignUpException(String message) {
        super(message);
    }
}

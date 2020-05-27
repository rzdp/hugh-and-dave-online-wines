package com.rzdp.winestoreapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class SshException extends RuntimeException {

    public SshException(String message) {
        super(message);
    }
}

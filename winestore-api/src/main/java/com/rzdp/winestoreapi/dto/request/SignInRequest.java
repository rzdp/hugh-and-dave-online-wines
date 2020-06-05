package com.rzdp.winestoreapi.dto.request;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class SignInRequest {

    @NotBlank(message = "{account.email.not-blank}")
    @Email(message = "{account.email.email}")
    private String username;

    @NotBlank(message = "{account.password.not-blank}")
    private String password;

}

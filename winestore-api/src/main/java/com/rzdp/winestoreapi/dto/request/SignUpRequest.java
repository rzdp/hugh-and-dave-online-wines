package com.rzdp.winestoreapi.dto.request;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class SignUpRequest {

    @NotBlank(message = "{user.first-name.not-blank}")
    @Size(max = 50, message = "{user.first-name.size}")
        @Pattern(regexp = "^[A-Za-z\\u00f1\\u00d1 ]+$", message = "{user.first-name.pattern}")
    private String firstName;

    @NotBlank(message = "{user.last-name.not-blank}")
    @Size(max = 50, message = "{user.last-name.size}")
    @Pattern(regexp = "^[A-Za-z\\u00f1\\u00d1 ]+$", message = "{user.last-name.pattern}")
    private String lastName;

    @NotBlank(message = "{contact.mobile.not-blank}")
    @Size(min = 10, max = 10, message = "{contact.mobile.size}")
    @Pattern(regexp = "^[0-9]*$", message = "{contact.mobile.pattern}")
    private String mobile;

    @NotBlank(message = "{account.email.not-blank}")
    @Size(max = 50, message = "{account.email.size}")
    @Email(message = "{account.email.email}")
    private String email;

    @NotBlank(message = "{account.password.not-blank}")
    @Size(min = 8, max = 128, message = "{account.password.size}")
    private String password;

}

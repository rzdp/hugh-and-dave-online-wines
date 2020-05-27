package com.rzdp.winestoreapi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class AccountDto {

    private Long accountId;

    @NotBlank(message = "{account.email.not-blank}")
    @Size(max = 50, message = "{account.email.size}")
    @Email(message = "{account.email.email}")
    private String email;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @NotBlank(message = "{account.password.not-blank}")
    @Size(min = 8, max = 128, message = "{account.password.size}")
    private String password;

    private boolean verified;
    private String role;
    private List<String> permissions;

}

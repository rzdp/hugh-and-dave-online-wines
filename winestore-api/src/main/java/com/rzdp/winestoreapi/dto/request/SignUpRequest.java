package com.rzdp.winestoreapi.dto.request;

import com.rzdp.winestoreapi.dto.AddressDto;
import com.rzdp.winestoreapi.dto.ContactDto;
import com.rzdp.winestoreapi.dto.NameDto;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
public class SignUpRequest {

    @NotBlank(message = "{user.salutation.not-blank}")
    @Size(min = 3, max = 5, message = "{user.salutation.size}")
    @Pattern(regexp = "^(Mrs?|Ms).$", message = "{user.salutation.pattern}")
    private String salutation;

    @Valid
    private NameDto name;

    @NotNull(message = "{user.birth-date.not-null}")
    private Date birthDate;

    @Valid
    private AddressDto address;

    @Valid
    private ContactDto contact;

    @NotBlank(message = "{account.email.not-blank}")
    @Size(max = 50, message = "{account.email.size}")
    @Email(message = "{account.email.email}")
    private String email;

    @NotBlank(message = "{account.password.not-blank}")
    @Size(min = 8, max = 128, message = "{account.password.size}")
    private String password;
}

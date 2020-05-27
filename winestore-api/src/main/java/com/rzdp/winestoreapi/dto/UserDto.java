package com.rzdp.winestoreapi.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Data
public class UserDto {

    private Long userId;

    @NotBlank(message = "{user.salutation.not-blank}")
    @Size(min = 3, max = 5, message = "{user.salutation.size}")
    @Pattern(regexp = "^(Mrs?|Ms).$", message = "{user.salutation.pattern}")
    private String salutation;
    private NameDto name;
    @NotNull(message = "{user.birth-date.not-null}")
    private Date birthDate;
    private boolean active;
    private List<String> images;
    private AddressDto address;
    private ContactDto contact;
    private AccountDto account;

}

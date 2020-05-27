package com.rzdp.winestoreapi.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class ContactDto {

    private Long contactId;

    @NotBlank(message = "{contact.mobile.not-blank}")
    @Size(min = 10, max = 10, message = "{contact.mobile.size}")
    @Pattern(regexp = "^[0-9]*$", message = "{contact.mobile.pattern}")
    private String mobile;

    @Size(min = 7, max = 10, message = "{contact.tel.size}")
    @Pattern(regexp = "^[0-9]*$", message = "{contact.tel.pattern}")
    private String tel;

    @Size(min = 7, max = 20, message = "{contact.fax.size}")
    @Pattern(regexp = "^[0-9]*$", message = "{contact.fax.pattern}")
    private String fax;
}

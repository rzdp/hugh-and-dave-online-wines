package com.rzdp.winestoreapi.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class AddressDto {

    private Long addressId;

    @NotBlank(message = "{address.line1.not-blank}")
    @Size(max = 100, message = "{address.line1.size}")
    private String line1;

    @Size(max = 100, message = "{address.line2.size}")
    private String line2;

    @Size(max = 100, message = "{address.line3.size}")
    private String line3;

    @NotBlank(message = "{address.city.not-blank}")
    @Size(max = 50, message = "{address.city.size}")
    private String city;

    @NotBlank(message = "{address.state.not-blank}")
    @Size(max = 50, message = "{address.state.size}")
    private String state;


    @NotBlank(message = "{address.country.not-blank}")
    @Size(max = 50, message = "{address.country.size}")
    private String country;


    @NotBlank(message = "{address.postal.not-blank}")
    @Size(max = 12, message = "{address.postal.size}")
    @Pattern(regexp = "^[0-9]*$", message = "{address.postal.pattern}")
    private String postal;

}

package com.rzdp.winestoreapi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NameDto {

    @NotBlank(message = "{user.first-name.not-blank}")
    @Size(max = 50, message = "{user.first-name.size}")
    @Pattern(regexp = "^[A-Za-z\\u00f1\\u00d1 ]+$", message = "{user.first-name.pattern}")
    private String first;

    @Size(max = 50, message = "{user.middle-name.size}")
    @Pattern(regexp = "^[A-Za-z\\u00f1\\u00d1 ]+$", message = "{user.middle-name.pattern}")
    private String middle;

    @NotBlank(message = "{user.last-name.not-blank}")
    @Size(max = 50, message = "{user.last-name.size}")
    @Pattern(regexp = "^[A-Za-z\\u00f1\\u00d1 ]+$", message = "{user.last-name.pattern}")
    private String last;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Size(min = 1, max = 5, message = "{user.suffix.size}")
    private String suffix;
}

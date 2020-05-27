package com.rzdp.winestoreapi.dto.response;


import com.rzdp.winestoreapi.dto.ErrorDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ValidationErrorResponse {

    private Integer code;
    private String message;
    private List<ErrorDto> errors;
}

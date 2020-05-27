package com.rzdp.winestoreapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorDto {

    private String field;
    private Object data;
    private String message;
}

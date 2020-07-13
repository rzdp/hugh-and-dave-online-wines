package com.rzdp.winestoreapi.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
public class MessageResponse {

    private String message;

    public MessageResponse(String message) {
        this.message = message;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object data;
}

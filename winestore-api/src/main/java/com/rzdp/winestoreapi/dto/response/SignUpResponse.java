package com.rzdp.winestoreapi.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SignUpResponse {

    private long userId;
    private String firstName;
    private String lastName;
    private String email;

}

package com.rzdp.winestoreapi.dto.response;

import com.rzdp.winestoreapi.dto.NameDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class SignInResponse {

    private String accessToken;
    private String salutation;
    private NameDto name;
    private String email;
    private String role;
    private List<String> permissions;

}

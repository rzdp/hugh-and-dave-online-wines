package com.rzdp.winestoreapi.controller;

import com.rzdp.winestoreapi.dto.UserDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("User Controller Integration Tests")
class UserControllerIntTest {

    private static final String BASE_URL = "http://localhost:";
    @LocalServerPort
    private int port;

    private TestRestTemplate testRestTemplate = new TestRestTemplate();
    private HttpHeaders httpHeaders = new HttpHeaders();

    @Test
    void getUserById() throws Exception {
        HttpEntity<String> entity = new HttpEntity<>(null, httpHeaders);
        ResponseEntity<UserDto> response = testRestTemplate.exchange(
                BASE_URL + port + "/api/v1/users/1",
                HttpMethod.GET,
                entity,
                UserDto.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getUserId()).isEqualTo(1);
    }

    @Test
    void updateUserPhoto() {

    }


}
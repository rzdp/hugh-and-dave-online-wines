package com.rzdp.winestoreapi.controller;

import com.rzdp.winestoreapi.config.properties.MessageProperties;
import com.rzdp.winestoreapi.dto.UserDto;
import com.rzdp.winestoreapi.dto.response.MessageResponse;
import com.rzdp.winestoreapi.mapper.UserToUserDtoMapper;
import com.rzdp.winestoreapi.service.user.impl.UserServiceImpl;
import com.rzdp.winestoreapi.util.TestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@SpringBootTest
@DisplayName("User Controller Tests")
class UserControllerTest {

    @Autowired
    private MessageProperties messageProperties;

    @InjectMocks
    private UserController userController;

    @Mock
    private UserServiceImpl userServiceMock;


    @BeforeEach
    void setUp() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addConverter(new UserToUserDtoMapper());

        when(userServiceMock.getUserById(anyLong()))
                .thenReturn(modelMapper.map(TestUtil.getUserData(), UserDto.class));

        when(userServiceMock.updateUserPhoto(anyLong(), any(MultipartFile.class)))
                .thenReturn(new MessageResponse(messageProperties.getSuccess().getUpdatePhoto()));
    }


    @Test
    @DisplayName("getUserById() returns user when successful")
    void getUserById_ReturnsUser_WhenSuccessful() {
        // Arrange
        long expectedUserId = TestUtil.getUserData().getUserId();

        // Act
        ResponseEntity<UserDto> responseEntity
                = userController.getUserById(expectedUserId);
        UserDto userDto = responseEntity.getBody();

        // Assert
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(userDto).isNotNull();
        assertThat(userDto.getUserId()).isNotNull();
        assertThat(userDto.getUserId()).isEqualTo(expectedUserId);
    }


    @Test
    @DisplayName("updateUserPhoto() returns success message when successful")
    void updateUserPhoto_ReturnsSuccessMessage_WhenSuccessful() throws IOException {
        // Arrange
        long userId = TestUtil.getUserData().getUserId();
        String expectedMessage = messageProperties.getSuccess().getUpdatePhoto();

        // Act
        ResponseEntity<MessageResponse> responseEntity =
                userController.updateUserPhoto(userId, TestUtil.getUserPhoto());

        // Assert
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getMessage()).isEqualTo(expectedMessage);
    }
}
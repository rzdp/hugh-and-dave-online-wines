package com.rzdp.winestoreapi.service.user.impl;

import com.rzdp.winestoreapi.config.properties.MessageProperties;
import com.rzdp.winestoreapi.entity.User;
import com.rzdp.winestoreapi.exception.DataNotFoundException;
import com.rzdp.winestoreapi.repository.UserRepository;
import com.rzdp.winestoreapi.service.user.GetUserById;
import com.rzdp.winestoreapi.util.TestUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@SpringBootTest
@DisplayName("Get User By Id Service Tests")
class GetUserByIdImplTest {

    @Autowired
    private MessageProperties messageProperties;

    @InjectMocks
    @Autowired
    private GetUserByIdImpl getUserById;

    @InjectMocks
    @Autowired
    private CreateUserImpl createUser;

    @Mock
    private UserRepository userRepositoryMock;

    @Test
    @DisplayName("run() returns user when successful")
    void run_ReturnsUser_WhenSuccessful() {
        // Arrange
        User user = TestUtil.getUserData();
        User savedUser = createUser.run(user);
        long expectedId = savedUser.getUserId();
        when(userRepositoryMock.findById(expectedId))
                .thenReturn(Optional.of(savedUser));

        // Act
        User userResult = getUserById.run(expectedId);

        // Assert
        assertThat(userResult).isNotNull();
        assertThat(userResult.getUserId()).isNotNull();
        assertThat(userResult.getUserId()).isEqualTo(expectedId);
    }

    @Test
    @DisplayName("run() throws DataNotFoundException when user does not exist")
    void run_ThrowDatNotFoundException_WhenUserDoesNotExists() {
        // Arrange
        long invalidUserId = 999999;
        when(userRepositoryMock.findById(invalidUserId))
                .thenReturn(Optional.empty());

        // Act and Assert
        assertThatExceptionOfType(DataNotFoundException.class)
                .isThrownBy(() -> getUserById.run(invalidUserId))
                .withMessage(messageProperties.getException().getDataNotFound().getUser());

    }
}
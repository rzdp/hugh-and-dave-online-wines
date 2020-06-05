package com.rzdp.winestoreapi.service.user.impl;

import com.rzdp.winestoreapi.entity.User;
import com.rzdp.winestoreapi.exception.DataNotFoundException;
import com.rzdp.winestoreapi.repository.UserRepository;
import com.rzdp.winestoreapi.util.TestUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@DisplayName("Get User By Id Service Tests")
@TestMethodOrder(MethodOrderer.Alphanumeric.class)
class GetUserByIdImplTest {

    @InjectMocks
    private GetUserByIdImpl getUserById;

    @Mock
    private UserRepository userRepositoryMock;

    @Test
    @DisplayName("run() returns user when user ID is valid")
    void run_ReturnsUser_WhenUserIdIsValid() {
        // Arrange
        User expectedUser = TestUtil.getUserData();
        long userId = expectedUser.getUserId();
        when(userRepositoryMock.findById(userId))
                .thenReturn(Optional.of(expectedUser));

        // Act
        User user = getUserById.run(userId);

        // Assert
        assertThat(user).isNotNull();
        assertThat(user).isEqualTo(expectedUser);
        assertThat(user.getUserId()).isEqualTo(userId);
    }

    @Test
    @DisplayName("run() throws DataNotFoundException when user ID is invalid")
    void run_ThrowsDataNotFoundException_WhenUserIdIsInvalid() {
        // Arrange
        long userId = 9999;
        when(userRepositoryMock.findById(userId))
                .thenThrow(DataNotFoundException.class);

        // Act & Assert
        assertThatExceptionOfType(DataNotFoundException.class)
                .isThrownBy(() -> getUserById.run(userId));
    }

}
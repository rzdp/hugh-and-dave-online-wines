package com.rzdp.winestoreapi.service.user.impl;

import com.rzdp.winestoreapi.entity.User;
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

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@DisplayName("Create User Service Tests")
@TestMethodOrder(MethodOrderer.Alphanumeric.class)
class CreateUserImplTest {

    @InjectMocks
    private CreateUserImpl createUser;

    @Mock
    private UserRepository userRepositoryMock;

    @Test
    @DisplayName("run() returns user when successful")
    void run_ReturnsUser_WhenSuccessful() {
        // Arrange
        User user = TestUtil.getUserData();
        when(userRepositoryMock.save(user)).thenReturn(user);

        // Act
        User savedUser = createUser.run(user);

        // Assert
        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getUserId()).isNotNull();
        assertThat(savedUser.getFullName()).isEqualTo(user.getFullName());
        assertThat(savedUser.getAccount()).isEqualTo(user.getAccount());
    }
}
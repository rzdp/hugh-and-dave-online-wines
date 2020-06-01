package com.rzdp.winestoreapi.service.user.impl;

import com.rzdp.winestoreapi.entity.User;
import com.rzdp.winestoreapi.repository.UserRepository;
import com.rzdp.winestoreapi.util.TestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@DisplayName("Create User Service Tests")
class CreateUserImplTest {

    @InjectMocks
    private CreateUserImpl createUser;

    @Mock
    private UserRepository userRepositoryMock;

    @BeforeEach
    void setUp() {
        when(userRepositoryMock.save(any(User.class)))
                .thenReturn(TestUtil.getUserData());
    }


    @Test
    @DisplayName("run() creates user when successful")
    void run_CreatesUser_WhenSuccessful() {
        // Arrange
        User user = TestUtil.getUserData();

        // Act
        User savedUser = createUser.run(user);

        // Assert
        assertThat(savedUser.getUserId()).isNotNull();
        assertThat(savedUser.getFullName().trim()).isNotEmpty();
        assertThat(savedUser.getFullName()).isEqualTo(user.getFullName());
    }


}
package com.rzdp.winestoreapi.service.account.impl;

import com.rzdp.winestoreapi.entity.User;
import com.rzdp.winestoreapi.repository.AccountRepository;
import com.rzdp.winestoreapi.repository.UserRepository;
import com.rzdp.winestoreapi.service.user.impl.CreateUserImpl;
import com.rzdp.winestoreapi.util.TestUtil;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
@DisplayName("Exists Account By Email Service Tests")
class ExistsAccountByEmailImplTest {

    @Autowired
    private CreateUserImpl createUser;

    @InjectMocks
    private ExistsAccountByEmailImpl existsAccountByEmail;

    @Mock
    private AccountRepository accountRepositoryMock;

    @Test
    @DisplayName("run() returns true when user does exist")
    void run_ReturnTrue_WhenUserDoesExist() {
        // Arrange
        User savedUser = createUser.run(TestUtil.getUserData());
        String email = savedUser.getAccount().getEmail();
        when(accountRepositoryMock.existsByEmail(email))
                .thenReturn(true);

        // Act
        boolean result = existsAccountByEmail.run(email);

        // Assert
        assertThat(result).isEqualTo(true);
    }

    @Test
    @DisplayName("run() returns false when user does not exist")
    void run_ReturnFalse_WhenUserDoesNotExist() {
        String email = "dummyemail@gmail.com";

        when(accountRepositoryMock.existsByEmail(email))
                .thenReturn(false);

        // Act
        boolean result = existsAccountByEmail.run(email);

        // Assert
        assertThat(result).isEqualTo(false);
    }
}
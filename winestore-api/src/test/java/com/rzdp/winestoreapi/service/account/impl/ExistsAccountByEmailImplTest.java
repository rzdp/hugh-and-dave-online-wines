package com.rzdp.winestoreapi.service.account.impl;

import com.rzdp.winestoreapi.entity.User;
import com.rzdp.winestoreapi.repository.AccountRepository;
import com.rzdp.winestoreapi.service.user.impl.CreateUserImpl;
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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@DisplayName("Exists Account By Email Service Tests")
@TestMethodOrder(MethodOrderer.Alphanumeric.class)
class ExistsAccountByEmailImplTest {

    @InjectMocks
    private ExistsAccountByEmailImpl existsAccountByEmail;

    @Mock
    private AccountRepository accountRepositoryMock;

    @Test
    @DisplayName("run() returns true when account does exists")
    void run_ReturnsTrue_WhenAccountDoesExist() {
        // Arrange
        User user = TestUtil.getUserData();
        String email = user.getAccount().getEmail();
        boolean expectedResult = true;
        when(accountRepositoryMock.existsByEmail(email))
                .thenReturn(expectedResult);

        // Act
        boolean result = existsAccountByEmail.run(email);

        // Assert
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    @DisplayName("run() returns false when account does not exists")
    void run_ReturnsFalse_WhenAccountDoesNotExist() {
        // Arrange
        String email = "dummy@domain.com";
        boolean expectedResult = false;
        when(accountRepositoryMock.existsByEmail(email))
                .thenReturn(expectedResult);

        // Act
        boolean result = existsAccountByEmail.run(email);

        // Assert
        assertThat(result).isEqualTo(expectedResult);
    }
}
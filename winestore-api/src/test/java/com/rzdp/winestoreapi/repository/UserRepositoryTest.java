package com.rzdp.winestoreapi.repository;

import com.rzdp.winestoreapi.entity.Account;
import com.rzdp.winestoreapi.entity.Address;
import com.rzdp.winestoreapi.entity.Contact;
import com.rzdp.winestoreapi.entity.User;
import com.rzdp.winestoreapi.util.TestUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;


@DataJpaTest
@DisplayName("User Repository Tests")
@TestMethodOrder(MethodOrderer.Alphanumeric.class)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("findAll() returns list of all users when users does exist from database")
    void findAll_ReturnsList_WhenUsersDoesExistFromDatabase() {
        // Arrange
        userRepository.save(TestUtil.getUserData());

        // Act
        List<User> users = userRepository.findAll();

        // Assert
        assertThat(users).isNotEmpty();
    }

    @Test
    @DisplayName("findAll() returns empty list of users when users does not exist from database")
    void findAll_ReturnsEmptyList_WhenUsersDoesNotExistFromDatabase() {
        // Act
        List<User> users = userRepository.findAll();

        // Assert
        assertThat(users).isEmpty();
    }

    @Test
    @DisplayName("findById() returns non-empty optional user when user ID does exist from database")
    void findById_ReturnsNonEmptyOptionalUser_WhenUserIdDoesExistFromDatabase() {
        // Arrange
        User savedUser = userRepository.save(TestUtil.getUserData());

        // Act
        Optional<User> optionalUser = userRepository.findById(savedUser.getUserId());

        // Assert
        assertThat(optionalUser.isPresent()).isTrue();
        assertThat(optionalUser.get()).isNotNull();
    }

    @Test
    @DisplayName("findById() returns empty optional user when user ID does not exist from database")
    void findById_ReturnsEmptyOptionalUser_WhenUserIdDoesNotExistFromDatabase() {
        // Arrange
        long userId = 1000;

        // Act
        Optional<User> optionalUser = userRepository.findById(userId);

        // Assert
        assertThat(optionalUser.isPresent()).isFalse();
        assertThat(optionalUser).isEqualTo(Optional.empty());
    }

    @Test
    @DisplayName("save() creates a user when successful")
    void save_CreateUser_WhenSuccessful() {
        // Arrange
        User user = TestUtil.getUserData();

        // Act
        User savedUser = userRepository.save(user);

        // Assert
        assertThat(savedUser.getUserId()).isNotNull();
        assertThat(savedUser.getFullName().trim()).isNotEmpty();
        assertThat(savedUser.getFullName()).isEqualTo(user.getFullName());
    }

    @Test
    @DisplayName("save() updates a user when successful")
    void save_UpdateUser_WhenSuccessful() {
        // Arrange
        User user = TestUtil.getUserData();

        // Act
        User savedUser = userRepository.save(user);
        savedUser.setFirstName("Maxwell");

        Address address = savedUser.getAddress();
        address.setLine1("8430");

        Contact contact = savedUser.getContact();
        contact.setMobile("9308432999");

        Account account = savedUser.getAccount();
        account.setEmail("ggdelacruz@gmail.com");

        User updatedUser = userRepository.save(savedUser);

        // Assert
        assertThat(savedUser.getUserId()).isNotNull();
        assertThat(savedUser.getUserId()).isEqualTo(updatedUser.getUserId());
        assertThat(updatedUser.getFirstName()).isEqualTo(savedUser.getFirstName());
        assertThat(updatedUser.getAddress().getLine1()).isEqualTo(address.getLine1());
        assertThat(updatedUser.getContact().getMobile()).isEqualTo(contact.getMobile());
        assertThat(updatedUser.getAccount().getEmail()).isEqualTo(account.getEmail());
    }


    @Test
    @DisplayName("save() throws a ConstraintViolationException when some of user details are " +
            "invalid")
    void save_ThrowsConstraintViolationException_WhenSomeUserDetailsAreInvalid() {
        // Arrange
        User user = TestUtil.getUserData();
        user.setSalutation("INVALID_DATA");

        // Act and Assert
        assertThatExceptionOfType(ConstraintViolationException.class)
                .isThrownBy(() -> userRepository.save(user));
    }

}
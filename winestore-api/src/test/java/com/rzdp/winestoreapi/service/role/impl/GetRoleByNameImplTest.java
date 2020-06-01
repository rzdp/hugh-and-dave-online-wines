package com.rzdp.winestoreapi.service.role.impl;

import com.rzdp.winestoreapi.config.properties.MessageProperties;
import com.rzdp.winestoreapi.entity.Role;
import com.rzdp.winestoreapi.exception.DataNotFoundException;
import com.rzdp.winestoreapi.repository.RoleRepository;
import com.rzdp.winestoreapi.util.TestUtil;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
@DisplayName("Get Role By Name Service Tests")
class GetRoleByNameImplTest {

    @Autowired
    private MessageProperties messageProperties;

    @InjectMocks
    private GetRoleByNameImpl getRoleByName;

    @Mock
    private RoleRepository roleRepositoryMock;

    @Test
    void run_ReturnRole_WhenSuccessful() {
        // Arrange
        Role role = TestUtil.getRoleData();
        String expectedName = role.getName();
        when(roleRepositoryMock.findByName(expectedName))
                .thenReturn(Optional.of(role));

        // Act
        Role roleResult = getRoleByName.run(expectedName);

        // Assert
        assertThat(roleResult).isNotNull();
        assertThat(roleResult.getName()).isNotEmpty();
        assertThat(roleResult.getName()).isEqualTo(expectedName);
    }

    @Test
    void run_ThrowDataNotFoundException_WhenRoleDoesNotExists() {
        // Arrange
        Role role = TestUtil.getRoleData();
        String name = "INVALID_ROLE";
        when(roleRepositoryMock.findByName(name))
                .thenReturn(Optional.empty());

        // Act and Assert
        Assertions.assertThatExceptionOfType(DataNotFoundException.class)
                .isThrownBy(() -> getRoleByName.run(name))
                .withMessage(messageProperties.getException().getRole().getDataNotFound());
    }
}
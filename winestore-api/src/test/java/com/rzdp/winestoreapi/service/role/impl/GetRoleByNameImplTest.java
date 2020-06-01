package com.rzdp.winestoreapi.service.role.impl;

import com.rzdp.winestoreapi.config.properties.MessageProperties;
import com.rzdp.winestoreapi.entity.Role;
import com.rzdp.winestoreapi.exception.DataNotFoundException;
import com.rzdp.winestoreapi.repository.RoleRepository;
import com.rzdp.winestoreapi.util.TestUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
@DisplayName("Get Role By Name Service Tests")
class GetRoleByNameImplTest {

    @Autowired
    private MessageProperties messageProperties;

    @InjectMocks
    @Autowired
    private GetRoleByNameImpl getRoleByName;

    @Mock
    private RoleRepository roleRepositoryMock;



    @Test
    @DisplayName("run() returns role when successful")
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
    @DisplayName("run() throws DataNotFoundException when role does not exist")
    void run_ThrowDataNotFoundException_WhenRoleDoesNotExists() {
        // Arrange
        String name = "INVALID_ROLE";
        when(roleRepositoryMock.findByName(name))
                .thenReturn(Optional.empty());

        // Act and Assert
        assertThatExceptionOfType(DataNotFoundException.class)
                .isThrownBy(() -> getRoleByName.run(name))
                .withMessage(messageProperties.getException().getDataNotFound().getRole());
    }
}
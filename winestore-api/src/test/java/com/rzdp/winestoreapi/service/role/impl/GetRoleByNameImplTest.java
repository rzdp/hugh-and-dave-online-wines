package com.rzdp.winestoreapi.service.role.impl;

import com.rzdp.winestoreapi.constant.UserRole;
import com.rzdp.winestoreapi.entity.Role;
import com.rzdp.winestoreapi.exception.DataNotFoundException;
import com.rzdp.winestoreapi.repository.RoleRepository;
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
@DisplayName("Get Role By Name Service Tests")
@TestMethodOrder(MethodOrderer.Alphanumeric.class)
class GetRoleByNameImplTest {

    @InjectMocks
    private GetRoleByNameImpl getRoleByName;

    @Mock
    private RoleRepository roleRepositoryMock;

    @Test
    @DisplayName("run() returns role when name is valid")
    void run_ReturnsRole_WhenNameIsValid() {
        // Arrange
        String roleName = UserRole.ROLE_USER;
        Role expectedResult = TestUtil.getRoleData();
        when(roleRepositoryMock.findByName(roleName))
                .thenReturn(Optional.of(expectedResult));

        // Act
        Role role = getRoleByName.run(roleName);

        // Assert
        assertThat(role).isNotNull();
        assertThat(role).isEqualTo(expectedResult);
        assertThat(role.getName()).isEqualTo(roleName);
    }

    @Test
    @DisplayName("run() throws data not found exception when name is invalid")
    void run_ThrowsDataNotFoundException_WhenNameIsInvalid() {
        // Arrange
        String roleName = "ROLE_DUMMY";
        when(roleRepositoryMock.findByName(roleName))
                .thenThrow(DataNotFoundException.class);

        // Act & Assert
        assertThatExceptionOfType(DataNotFoundException.class)
                .isThrownBy(() -> getRoleByName.run(roleName));
    }
}
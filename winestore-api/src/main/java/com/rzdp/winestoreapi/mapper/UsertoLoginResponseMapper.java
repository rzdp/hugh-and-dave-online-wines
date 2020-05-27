package com.rzdp.winestoreapi.mapper;

import com.rzdp.winestoreapi.dto.NameDto;
import com.rzdp.winestoreapi.dto.response.SignInResponse;
import com.rzdp.winestoreapi.entity.Account;
import com.rzdp.winestoreapi.entity.Permission;
import com.rzdp.winestoreapi.entity.Role;
import com.rzdp.winestoreapi.entity.User;
import org.modelmapper.AbstractConverter;

import java.util.List;
import java.util.stream.Collectors;

public class UsertoLoginResponseMapper extends AbstractConverter<User, SignInResponse> {

    @Override
    protected SignInResponse convert(User user) {
        SignInResponse response = new SignInResponse();
        response.setSalutation(user.getSalutation());
        response.setName(new NameDto(user.getFirstName(), user.getMiddleName(),
                user.getLastName(), user.getSuffix()));

        Account account = user.getAccount();
        response.setEmail(account.getEmail());

        Role role = account.getRole();
        response.setRole(role.getName());

        List<Permission> permissions = role.getPermissions();
        response.setPermissions(permissions.stream()
                .map(Permission::getName).collect(Collectors.toList()));

        return response;
    }
}

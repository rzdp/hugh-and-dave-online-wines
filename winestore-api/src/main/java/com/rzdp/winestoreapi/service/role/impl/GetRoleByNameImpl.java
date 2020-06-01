package com.rzdp.winestoreapi.service.role.impl;

import com.rzdp.winestoreapi.config.properties.MessageProperties;
import com.rzdp.winestoreapi.entity.Role;
import com.rzdp.winestoreapi.exception.DataNotFoundException;
import com.rzdp.winestoreapi.repository.RoleRepository;
import com.rzdp.winestoreapi.service.role.GetRoleByName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GetRoleByNameImpl implements GetRoleByName {

    private final RoleRepository roleRepository;
    private final MessageProperties messageProperties;

    @Autowired
    public GetRoleByNameImpl(RoleRepository roleRepository,
                             MessageProperties messageProperties) {
        this.roleRepository = roleRepository;
        this.messageProperties = messageProperties;
    }

    @Override
    public Role run(String name) {
        Optional<Role> optionalRole = roleRepository.findByName(name);
        if (!optionalRole.isPresent()) {
            throw new DataNotFoundException(messageProperties
                    .getException().getDataNotFound().getRole());
        }
        return optionalRole.get();
    }
}

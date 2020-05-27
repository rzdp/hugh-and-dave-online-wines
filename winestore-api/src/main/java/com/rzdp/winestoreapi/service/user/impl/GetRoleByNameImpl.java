package com.rzdp.winestoreapi.service.user.impl;

import com.rzdp.winestoreapi.entity.Role;
import com.rzdp.winestoreapi.repository.RoleRepository;
import com.rzdp.winestoreapi.service.user.GetRoleByName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class GetRoleByNameImpl implements GetRoleByName {

    private final RoleRepository roleRepository;

    @Autowired
    public GetRoleByNameImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role run(String name) {
        Optional<Role> optionalRole = roleRepository.findByName(name);
        if (!optionalRole.isPresent()) {
            throw new EntityNotFoundException("Cannot find role with name of " + name);
        }
        return optionalRole.get();
    }
}

package com.rzdp.winestoreapi.service.user.impl;

import com.rzdp.winestoreapi.entity.User;
import com.rzdp.winestoreapi.repository.UserRepository;
import com.rzdp.winestoreapi.service.user.CreateUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateUserImpl implements CreateUser {

    private final UserRepository userRepository;

    @Autowired
    public CreateUserImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User run(User user) {
        return userRepository.save(user);
    }
}

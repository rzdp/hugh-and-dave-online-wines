package com.rzdp.winestoreapi.service.user.impl;

import com.rzdp.winestoreapi.entity.User;
import com.rzdp.winestoreapi.repository.UserRepository;
import com.rzdp.winestoreapi.service.user.GetUserById;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class GetUserByIdImpl implements GetUserById {

    private final UserRepository userRepository;

    @Autowired
    public GetUserByIdImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public User run(long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (!optionalUser.isPresent()) {
            throw new EntityNotFoundException("Cannot find user with user id of " + userId);
        }
        return optionalUser.get();
    }
}

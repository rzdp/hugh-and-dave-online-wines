package com.rzdp.winestoreapi.service.user.impl;

import com.rzdp.winestoreapi.config.properties.MessageProperties;
import com.rzdp.winestoreapi.entity.User;
import com.rzdp.winestoreapi.exception.DataNotFoundException;
import com.rzdp.winestoreapi.repository.UserRepository;
import com.rzdp.winestoreapi.service.user.GetUserById;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class GetUserByIdImpl implements GetUserById {

    private final UserRepository userRepository;
    private final MessageProperties messageProperties;

    @Autowired
    public GetUserByIdImpl(UserRepository userRepository,
                           MessageProperties messageProperties) {
        this.userRepository = userRepository;
        this.messageProperties = messageProperties;
    }

    @Override
    @Transactional
    public User run(long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (!optionalUser.isPresent()) {
            throw new DataNotFoundException(messageProperties.getException()
                    .getDataNotFound().getUser());
        }
        return optionalUser.get();
    }
}

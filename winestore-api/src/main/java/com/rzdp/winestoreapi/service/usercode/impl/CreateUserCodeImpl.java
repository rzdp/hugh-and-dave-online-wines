package com.rzdp.winestoreapi.service.usercode.impl;

import com.rzdp.winestoreapi.entity.UserCode;
import com.rzdp.winestoreapi.repository.UserCodeRepository;
import com.rzdp.winestoreapi.service.usercode.CreateUserCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CreateUserCodeImpl implements CreateUserCode {

    private final UserCodeRepository userCodeRepository;

    @Autowired
    public CreateUserCodeImpl(UserCodeRepository userCodeRepository) {
        this.userCodeRepository = userCodeRepository;
    }

    @Override
    public UserCode run(UserCode userCode) {
        Optional<UserCode> optionalCurrentCode = userCodeRepository
                .findByUserAndActive(userCode.getUser(), true);
        if (optionalCurrentCode.isPresent()) {
            UserCode currentCode = optionalCurrentCode.get();
            currentCode.setActive(false);
            userCodeRepository.save(currentCode);
        }
        return userCodeRepository.save(userCode);
    }

}

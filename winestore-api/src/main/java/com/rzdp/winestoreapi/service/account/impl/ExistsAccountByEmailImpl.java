package com.rzdp.winestoreapi.service.account.impl;

import com.rzdp.winestoreapi.repository.AccountRepository;
import com.rzdp.winestoreapi.service.account.ExistsAccountByEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExistsAccountByEmailImpl implements ExistsAccountByEmail {

    private final AccountRepository accountRepository;

    @Autowired
    public ExistsAccountByEmailImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public boolean run(String email) {
        return accountRepository.existsByEmail(email);
    }
}

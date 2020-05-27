package com.rzdp.winestoreapi.service.account.impl;

import com.rzdp.winestoreapi.repository.AccountRepository;
import com.rzdp.winestoreapi.service.account.ExistAccountByEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExistAccountByEmailImpl implements ExistAccountByEmail {

    private final AccountRepository accountRepository;

    @Autowired
    public ExistAccountByEmailImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public boolean run(String email) {
        return accountRepository.existByEmail(email);
    }
}

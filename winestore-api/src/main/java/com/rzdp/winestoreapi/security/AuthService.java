package com.rzdp.winestoreapi.security;

import com.rzdp.winestoreapi.entity.Account;
import com.rzdp.winestoreapi.repository.AccountRepository;
import com.rzdp.winestoreapi.service.user.GetUserById;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class AuthService implements UserDetailsService {

    private final AccountRepository accountRepository;
    private final GetUserById getUserById;

    @Autowired
    public AuthService(AccountRepository accountRepository, GetUserById getUserById) {
        this.accountRepository = accountRepository;
        this.getUserById = getUserById;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Account account = accountRepository.findByEmailAndVerified(email, true);
        if (account == null) {
            throw new UsernameNotFoundException("Invalid email or username: " + email);
        }
        return UserPrincipal.create(account.getUser());
    }

    @Transactional
    public UserDetails getUserDetailsFromUserId(long userId) {
        return UserPrincipal.create(getUserById.run(userId));
    }

}

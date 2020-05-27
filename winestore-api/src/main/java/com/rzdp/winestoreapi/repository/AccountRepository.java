package com.rzdp.winestoreapi.repository;

import com.rzdp.winestoreapi.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AccountRepository extends JpaRepository<Account, Long>,
        JpaSpecificationExecutor<Account> {

    Account findByEmailAndVerified(String email, boolean verified);

    boolean existByEmail(String email);
}

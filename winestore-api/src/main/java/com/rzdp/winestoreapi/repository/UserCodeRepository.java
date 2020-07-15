package com.rzdp.winestoreapi.repository;

import com.rzdp.winestoreapi.entity.User;
import com.rzdp.winestoreapi.entity.UserCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface UserCodeRepository extends JpaRepository<UserCode, Long>,
        JpaSpecificationExecutor<UserCode> {

    Optional<UserCode> findByUserAndActive(User user, boolean active);
}

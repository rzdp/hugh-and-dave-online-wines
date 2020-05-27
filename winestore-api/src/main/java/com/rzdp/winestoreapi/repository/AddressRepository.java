package com.rzdp.winestoreapi.repository;

import com.rzdp.winestoreapi.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AddressRepository extends JpaRepository<Address, Long>,
        JpaSpecificationExecutor<Address> {
}

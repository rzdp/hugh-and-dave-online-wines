package com.rzdp.winestoreapi.repository;

import com.rzdp.winestoreapi.entity.UserImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserImageRepository extends JpaRepository<UserImage, Long>,
        JpaSpecificationExecutor<UserImage> {
}

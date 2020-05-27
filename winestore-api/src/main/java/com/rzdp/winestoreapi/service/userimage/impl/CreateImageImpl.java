package com.rzdp.winestoreapi.service.userimage.impl;

import com.rzdp.winestoreapi.entity.User;
import com.rzdp.winestoreapi.entity.UserImage;
import com.rzdp.winestoreapi.repository.UserImageRepository;
import com.rzdp.winestoreapi.service.userimage.CreateImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateImageImpl implements CreateImage {

    private final UserImageRepository userImageRepository;

    @Autowired
    public CreateImageImpl(UserImageRepository userImageRepository) {
        this.userImageRepository = userImageRepository;
    }

    @Override
    public UserImage run(User user, String imagePath) {
        UserImage userImage = new UserImage();
        userImage.setUser(user);
        userImage.setImagePath(imagePath);
        return userImageRepository.save(userImage);
    }
}

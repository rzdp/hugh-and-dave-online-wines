package com.rzdp.winestoreapi.service.userimage;

import com.rzdp.winestoreapi.entity.User;
import com.rzdp.winestoreapi.entity.UserImage;

public interface CreateImage {

    UserImage run(User user, String imagePath);
}

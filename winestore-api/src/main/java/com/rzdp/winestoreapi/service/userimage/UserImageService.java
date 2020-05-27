package com.rzdp.winestoreapi.service.userimage;

import com.rzdp.winestoreapi.entity.User;
import com.rzdp.winestoreapi.entity.UserImage;

import java.io.File;
import java.io.IOException;

public interface UserImageService {

    UserImage createImage(User user, String imagePath);

    File createImageFile(long userId, byte[] data, int size) throws IOException;

    String createFilename(long userId, int size);

    String getFilepathSize(int size);

}

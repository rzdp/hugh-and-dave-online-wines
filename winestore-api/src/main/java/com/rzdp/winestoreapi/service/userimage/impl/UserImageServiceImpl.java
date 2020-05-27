package com.rzdp.winestoreapi.service.userimage.impl;

import com.rzdp.winestoreapi.config.properties.ImageUserProperties;
import com.rzdp.winestoreapi.entity.User;
import com.rzdp.winestoreapi.entity.UserImage;
import com.rzdp.winestoreapi.service.userimage.CreateImage;
import com.rzdp.winestoreapi.service.userimage.UserImageService;
import com.rzdp.winestoreapi.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

@Component
public class UserImageServiceImpl implements UserImageService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final CreateImage createImage;
    private final ImageUserProperties imageUserProperties;

    @Autowired
    public UserImageServiceImpl(CreateImage createImage,
                                ImageUserProperties imageUserProperties) {
        this.createImage = createImage;
        this.imageUserProperties = imageUserProperties;
    }

    @Override
    public UserImage createImage(User user, String imagePath) {
        return createImage.run(user, imagePath);
    }

    @Override
    public File createImageFile(long userId, byte[] data, int size) throws IOException {
        // Create filename
        String filename = createFilename(userId, size);
        String filePath = imageUserProperties.getToProcessPath() + filename;

        // Convert byte data to image
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data);
        BufferedImage bufferedImage = ImageIO.read(byteArrayInputStream);

        // Resize and create image
        log.info("Creating {} image", filename);
        return writeImage(bufferedImage, filePath, size, size);
    }

    @Override
    public String createFilename(long userId, int size) {
        String prefix = imageUserProperties.getPrefix();
        String filenameUserId = StringUtils.padLeft(String.valueOf(userId), 6, "0");
        String filenameSize = size + "X" + size;
        String extension = imageUserProperties.getExtension();
        String filepathSize = getFilepathSize(size).substring(0, 2).toUpperCase();
        return filepathSize + "_" + prefix + filenameUserId + "_" + filenameSize + extension;
    }

    @Override
    public String getFilepathSize(int size) {
        if (size == imageUserProperties.getSmall()) {
            return "small";
        } else if (size == imageUserProperties.getMedium()) {
            return "medium";
        } else {
            return "large";
        }
    }

    private File writeImage(BufferedImage image, String filePath, int width, int height)
            throws IOException {
        Image scaledInstance = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage bufferedImage = convertImageToBufferedImage(scaledInstance);
        File imageFile = new File(filePath);
        boolean fileCreated = ImageIO.write(bufferedImage, imageUserProperties.getFormat(),
                imageFile);
        if (fileCreated) {
            return imageFile;
        }
        return null;
    }

    private BufferedImage convertImageToBufferedImage(Image image) {
        BufferedImage bufferedImage = new BufferedImage(image.getWidth(null),
                image.getHeight(null), BufferedImage.TYPE_INT_RGB);
        Graphics graphics = bufferedImage.getGraphics();
        graphics.drawImage(image, 0, 0, null);
        graphics.dispose();
        return bufferedImage;
    }
}

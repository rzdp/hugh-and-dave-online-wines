package com.rzdp.winestoreapi.service.userimage.impl;

import com.rzdp.winestoreapi.entity.User;
import com.rzdp.winestoreapi.entity.UserImage;
import com.rzdp.winestoreapi.repository.UserImageRepository;
import com.rzdp.winestoreapi.util.TestUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@DisplayName("Create Image Service Tests")
class CreateImageImplTest {

    @InjectMocks
    private CreateImageImpl createImage;

    @Mock
    private UserImageRepository userImageRepositoryMock;

    @Test
    @DisplayName("run() returns UserImage when successful")
    void run_ReturnsUserImage_WhenSuccessful() {
        // Arrange
        User user = TestUtil.getUserData();
        UserImage userImage = new UserImage();
        userImage.setUser(user);
        userImage.setUserImageId((long) 1);
        userImage.setImagePath("image.jpg");
        when(userImageRepositoryMock.save(any(UserImage.class)))
                .thenReturn(userImage);

        // Act
        UserImage userImageResult = createImage.run(user, userImage.getImagePath());

        // Assert
        assertThat(userImageResult).isNotNull();
        assertThat(userImageResult.getImagePath()).isNotNull();
        assertThat(userImageResult.getImagePath()).isEqualTo(userImage.getImagePath());
    }

}
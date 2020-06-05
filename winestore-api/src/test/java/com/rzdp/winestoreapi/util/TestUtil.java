package com.rzdp.winestoreapi.util;

import com.rzdp.winestoreapi.constant.UserRole;
import com.rzdp.winestoreapi.dto.UserDto;
import com.rzdp.winestoreapi.dto.response.SignInResponse;
import com.rzdp.winestoreapi.entity.Account;
import com.rzdp.winestoreapi.entity.Address;
import com.rzdp.winestoreapi.entity.Contact;
import com.rzdp.winestoreapi.entity.Permission;
import com.rzdp.winestoreapi.entity.Role;
import com.rzdp.winestoreapi.entity.User;
import com.rzdp.winestoreapi.entity.UserImage;
import com.rzdp.winestoreapi.mapper.UserToUserDtoMapper;
import com.rzdp.winestoreapi.mapper.UsertoLoginResponseMapper;
import org.modelmapper.ModelMapper;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public final class TestUtil {

    public static User getUserData() {
        Address address = Address.builder()
                .addressId((long) 1)
                .line1("404")
                .line2("Daangbakal Street, Sto. Niño")
                .line3("Marikina City, Metro Manila")
                .city("Marikina City")
                .state("National Capital Region (NCR)")
                .country("Philippines")
                .postal("1800")
                .build();

        Contact contact = Contact.builder()
                .contactId((long) 1)
                .mobile("9308842422")
                .tel(null)
                .fax(null)
                .build();

        Role role = getRoleData();

        Account account = Account.builder()
                .accountId((long) 1)
                .email("jdelacruz@gmail.com")
                .password("P@ssw0rd")
                .verified(true)
                .role(role)
                .build();

        UserImage smallUserImage = UserImage.builder()
                .userImageId((long) 1)
                .imagePath("/WINESTORE/USERS/IMAGES/SM_USR_000001_1000X1000.jpg")
                .build();

        UserImage mediumUserImage = UserImage.builder()
                .userImageId((long) 2)
                .imagePath("/WINESTORE/USERS/IMAGES/ME_USR_000001_1000X1000.jpg")
                .build();

        UserImage largeUserImage = UserImage.builder()
                .userImageId((long) 3)
                .imagePath("/WINESTORE/USERS/IMAGES/LA_USR_000001_1000X1000.jpg")
                .build();

        User user = User.builder()
                .userId((long) 1)
                .salutation("Mr.")
                .firstName("Juan")
                .middleName("Dela Cruz")
                .lastName("Santos")
                .suffix(null)
                .birthDate(new Date())
                .active(true)
                .address(address)
                .contact(contact)
                .account(account)
                .userImages(Arrays.asList(smallUserImage, mediumUserImage, largeUserImage))
                .build();


        return user;
    }

    public static Role getRoleData() {
        return Role.builder()
                .roleId((long) 1)
                .name(UserRole.ROLE_USER)
                .permissions(getPermissionData())
                .build();
    }

    public static List<Permission> getPermissionData() {
        Permission permission1 = Permission.builder()
                .permissionId((long) 1)
                .name("READ_WINE")
                .build();

        Permission permission2 = Permission.builder()
                .permissionId((long) 2)
                .name("READ_USER")
                .build();

        return Arrays.asList(permission1, permission2);
    }

    public static MockMultipartFile getUserPhoto() throws IOException {
        return new MockMultipartFile("user-photo",
                "user-photo.jpg", "jpg", "image".getBytes());
    }

    public static UserDto getUserDto() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addConverter(new UserToUserDtoMapper());
        return modelMapper.map(getUserData(), UserDto.class);
    }

    public static SignInResponse getSignInResponse() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addConverter(new UsertoLoginResponseMapper());
        return modelMapper.map(getUserData(), SignInResponse.class);
    }
}

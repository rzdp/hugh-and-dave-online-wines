package com.rzdp.winestoreapi.mapper;

import com.rzdp.winestoreapi.dto.AccountDto;
import com.rzdp.winestoreapi.dto.AddressDto;
import com.rzdp.winestoreapi.dto.ContactDto;
import com.rzdp.winestoreapi.dto.NameDto;
import com.rzdp.winestoreapi.dto.UserDto;
import com.rzdp.winestoreapi.entity.Account;
import com.rzdp.winestoreapi.entity.Address;
import com.rzdp.winestoreapi.entity.Contact;
import com.rzdp.winestoreapi.entity.Permission;
import com.rzdp.winestoreapi.entity.Role;
import com.rzdp.winestoreapi.entity.User;
import com.rzdp.winestoreapi.entity.UserImage;
import org.modelmapper.AbstractConverter;

import java.util.List;
import java.util.stream.Collectors;

public class UserToUserDtoMapper extends AbstractConverter<User, UserDto> {

    @Override
    protected UserDto convert(User user) {
        // Map Address
        Address address = user.getAddress();
        AddressDto addressDto = new AddressDto();
        addressDto.setAddressId(address.getAddressId());
        addressDto.setLine1(address.getLine1());
        addressDto.setLine2(address.getLine2());
        addressDto.setLine3(address.getLine3());
        addressDto.setCity(address.getCity());
        addressDto.setState(address.getState());
        addressDto.setCountry(address.getPostal());
        addressDto.setPostal(address.getPostal());

        // Map Contact
        Contact contact = user.getContact();
        ContactDto contactDto = new ContactDto();
        contactDto.setContactId(contact.getContactId());
        contactDto.setMobile(contact.getMobile());
        contactDto.setTel(contact.getTel());
        contactDto.setFax(contact.getFax());

        // Map Account
        Account account = user.getAccount();
        Role role = account.getRole();
        List<Permission> permissions = role.getPermissions();
        AccountDto accountDto = new AccountDto();
        accountDto.setAccountId(account.getAccountId());
        accountDto.setEmail(account.getEmail());
        accountDto.setPassword(null);
        accountDto.setVerified(account.isVerified());
        accountDto.setRole(role.getName());
        accountDto.setPermissions(permissions.stream()
                .map(Permission::getName).collect(Collectors.toList()));

        // Map User
        UserDto userDto = new UserDto();
        userDto.setUserId(user.getUserId());
        userDto.setSalutation(user.getSalutation());
        userDto.setName(new NameDto(user.getFirstName(), user.getMiddleName(),
                user.getLastName(), user.getSuffix()));
        userDto.setBirthDate(user.getBirthDate());
        userDto.setImages(user.getUserImages().stream()
                .map(UserImage::getImagePath).collect(Collectors.toList()));
        userDto.setActive(user.isActive());
        userDto.setAddress(addressDto);
        userDto.setContact(contactDto);
        userDto.setAccount(accountDto);
        return userDto;
    }
}

package com.rzdp.winestoreapi.mapper;

import com.rzdp.winestoreapi.dto.AddressDto;
import com.rzdp.winestoreapi.dto.ContactDto;
import com.rzdp.winestoreapi.dto.NameDto;
import com.rzdp.winestoreapi.dto.request.SignUpRequest;
import com.rzdp.winestoreapi.entity.Account;
import com.rzdp.winestoreapi.entity.Address;
import com.rzdp.winestoreapi.entity.Contact;
import com.rzdp.winestoreapi.entity.User;
import org.modelmapper.AbstractConverter;


public class RegisterRequestToUserMapper extends AbstractConverter<SignUpRequest, User> {

    @Override
    protected User convert(SignUpRequest request) {

        // Map Contact
        Contact contact = new Contact();
        contact.setMobile(request.getMobile());

        // Map Account
        Account account = new Account();
        account.setEmail(request.getEmail());
        account.setPassword(request.getPassword());
        account.setVerified(false);

        // Map User
        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setContact(contact);
        user.setAccount(account);
        return user;
    }
}

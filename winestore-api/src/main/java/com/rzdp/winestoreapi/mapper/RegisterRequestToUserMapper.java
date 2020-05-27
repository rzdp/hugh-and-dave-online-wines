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

        // Map Address
        AddressDto addressDto = request.getAddress();
        Address address = new Address();
        address.setLine1(addressDto.getLine1());
        address.setLine2(addressDto.getLine2());
        address.setLine3(addressDto.getLine3());
        address.setCity(addressDto.getCity());
        address.setState(addressDto.getState());
        address.setCountry(addressDto.getCountry());
        address.setPostal(addressDto.getPostal());

        // Map Contact
        ContactDto contactDto = request.getContact();
        Contact contact = new Contact();
        contact.setMobile(contactDto.getMobile());
        contact.setTel(contactDto.getTel());
        contact.setFax(contactDto.getFax());

        // Map Account
        Account account = new Account();
        account.setEmail(request.getEmail());
        account.setPassword(request.getPassword());
        account.setVerified(false);

        // Map User
        NameDto name = request.getName();
        User user = new User();
        user.setSalutation(request.getSalutation());
        user.setFirstName(name.getFirst());
        user.setMiddleName(name.getMiddle());
        user.setLastName(name.getLast());
        user.setSuffix(name.getSuffix());
        user.setBirthDate(request.getBirthDate());
        user.setAddress(address);
        user.setContact(contact);
        user.setAccount(account);
        return user;
    }
}

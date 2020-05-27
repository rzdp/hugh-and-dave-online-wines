package com.rzdp.winestoreapi.service.email;

import com.rzdp.winestoreapi.dto.MailDto;

public interface EmailService {

    boolean sendUserVerificationEmail(MailDto mailDto);
}

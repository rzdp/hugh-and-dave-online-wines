package com.rzdp.winestoreapi.service.email;

import com.rzdp.winestoreapi.entity.User;

public interface EmailService {

    void sendUserVerificationEmail(User user, String email);
}

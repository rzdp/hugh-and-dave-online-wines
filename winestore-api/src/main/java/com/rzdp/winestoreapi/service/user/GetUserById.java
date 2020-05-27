package com.rzdp.winestoreapi.service.user;

import com.rzdp.winestoreapi.entity.User;

public interface GetUserById {

    User run(long userId);
}

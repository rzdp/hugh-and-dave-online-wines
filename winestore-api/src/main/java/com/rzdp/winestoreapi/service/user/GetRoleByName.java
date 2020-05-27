package com.rzdp.winestoreapi.service.user;

import com.rzdp.winestoreapi.entity.Role;

public interface GetRoleByName {

    Role run(String name);
}

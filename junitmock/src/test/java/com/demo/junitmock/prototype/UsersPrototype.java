package com.demo.junitmock.prototype;

import com.demo.junitmock.entity.Users;

public class UsersPrototype {
    public static Users aUser() {
        Users u = new Users();
        u.setName("test_name");
        u.setLogin("test_login");
        return u;
    }

}

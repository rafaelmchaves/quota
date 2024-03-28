package com.vicarius.quota.services;


import com.vicarius.quota.model.User;

public interface UserService {

    User create(User user);
    User update(User user);
    User get(String id);
    User delete(String id);

}

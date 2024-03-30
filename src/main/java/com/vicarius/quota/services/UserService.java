package com.vicarius.quota.services;


import com.vicarius.quota.model.User;

import java.util.List;

public interface UserService {

    User create(User user);
    User update(User user);
    User get(String id);
    void delete(String id);

    List<User> findAll();

    void lockUser(User user);

}

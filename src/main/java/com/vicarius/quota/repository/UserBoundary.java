package com.vicarius.quota.repository;

import com.vicarius.quota.model.User;

import java.util.List;
import java.util.UUID;

public interface UserBoundary {

    User save(User user);
    User get(UUID id);
    User update(User user);
    void delete(UUID id);

    List<User> findAll();

}

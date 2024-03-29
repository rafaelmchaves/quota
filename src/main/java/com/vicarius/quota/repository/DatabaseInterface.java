package com.vicarius.quota.repository;

import com.vicarius.quota.model.User;

import java.util.UUID;

public interface DatabaseInterface {

    User save(User user);
    User get(UUID id);
    User update(User user);
    User delete(UUID id);

}

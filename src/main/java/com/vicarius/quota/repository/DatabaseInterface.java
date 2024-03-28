package com.vicarius.quota.repository;

import com.vicarius.quota.model.User;

public interface DatabaseInterface {

    User save(User user);

}

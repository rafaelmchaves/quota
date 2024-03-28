package com.vicarius.quota.services;

import com.vicarius.quota.model.User;
import com.vicarius.quota.repository.DatabaseFactory;
import com.vicarius.quota.repository.DatabaseInterface;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    DatabaseInterface databaseInterface = new DatabaseFactory().getDatabase();

    @Override
    public User create(User user) {

        return null;
    }

    @Override
    public User update(User user) {
        return null;
    }

    @Override
    public User get(String id) {
        return null;
    }

    @Override
    public User delete(String id) {
        return null;
    }
}

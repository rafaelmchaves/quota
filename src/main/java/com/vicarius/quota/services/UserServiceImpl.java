package com.vicarius.quota.services;

import com.vicarius.quota.model.Status;
import com.vicarius.quota.model.User;
import com.vicarius.quota.repository.DatabaseFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final DatabaseFactory databaseFactory;

    @Override
    public User create(User user) {

        user.setCreation(LocalDateTime.now());
        user.setStatus(Status.ACTIVE);
        return databaseFactory.getDatabase().save(user);
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

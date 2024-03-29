package com.vicarius.quota.services.impl;

import com.vicarius.quota.model.Status;
import com.vicarius.quota.model.User;
import com.vicarius.quota.repository.DatabaseFactory;
import com.vicarius.quota.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

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
        user.setUpdate(LocalDateTime.now());
        final var foundUser = databaseFactory.getDatabase().get(UUID.fromString(user.getId()));
        if (foundUser == null) {
            throw new RuntimeException("User not found");
        }

        foundUser.setStatus(user.getStatus());
        foundUser.setFirstName(user.getFirstName());
        foundUser.setLastName(user.getLastName());
        foundUser.setUpdate(LocalDateTime.now());

        return databaseFactory.getDatabase().update(foundUser);
    }

    @Override
    public User get(String id) {
        return databaseFactory.getDatabase().get(UUID.fromString(id));
    }

    @Override
    public void delete(String id) {
        databaseFactory.getDatabase().delete(UUID.fromString(id));
    }
}

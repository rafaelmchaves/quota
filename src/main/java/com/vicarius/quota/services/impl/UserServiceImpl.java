package com.vicarius.quota.services.impl;

import com.vicarius.quota.model.Status;
import com.vicarius.quota.model.User;
import com.vicarius.quota.repository.DatabaseFactory;
import com.vicarius.quota.repository.DatabaseInterface;
import com.vicarius.quota.repository.elastic.ElasticImplementation;
import com.vicarius.quota.repository.mysql.MySqlImplementation;
import com.vicarius.quota.services.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;
@Service
public class UserServiceImpl implements UserService {

    private final DatabaseFactory databaseFactory;

    private final DatabaseInterface mysqlDao;

    private final DatabaseInterface elasticDao;

    public UserServiceImpl(DatabaseFactory databaseFactory, @Qualifier(MySqlImplementation.IMPLEMENTATION_ID) DatabaseInterface mysqlDao,
                           @Qualifier(ElasticImplementation.IMPLEMENTATION_ID) DatabaseInterface elasticDao) {
        this.databaseFactory = databaseFactory;
        this.mysqlDao = mysqlDao;
        this.elasticDao = elasticDao;
    }

    @Transactional
    @Override
    public User create(User user) {

        User persistedUser = null;

        try {
            user.setCreation(LocalDateTime.now());
            user.setStatus(Status.ACTIVE);

            persistedUser = mysqlDao.save(user);
            elasticDao.save(user);
        } catch (Exception e) {
            throw new RuntimeException("It was not persisted in the database", e);
        }

        return persistedUser;
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

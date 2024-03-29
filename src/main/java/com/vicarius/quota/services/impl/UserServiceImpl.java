package com.vicarius.quota.services.impl;

import com.vicarius.quota.model.Status;
import com.vicarius.quota.model.User;
import com.vicarius.quota.repository.DatabaseStrategy;
import com.vicarius.quota.repository.UserBoundary;
import com.vicarius.quota.repository.elastic.ElasticImplementation;
import com.vicarius.quota.repository.mysql.MySqlImplementation;
import com.vicarius.quota.services.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
@Service
public class UserServiceImpl implements UserService {

    private final DatabaseStrategy databaseStrategy;

    private final UserBoundary mysqlDao;

    private final UserBoundary elasticDao;

    public UserServiceImpl(DatabaseStrategy databaseStrategy, @Qualifier(MySqlImplementation.IMPLEMENTATION_ID) UserBoundary mysqlDao,
                           @Qualifier(ElasticImplementation.IMPLEMENTATION_ID) UserBoundary elasticDao) {
        this.databaseStrategy = databaseStrategy;
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

    @Transactional
    @Override
    public User update(User user) {

        try {
            user.setUpdate(LocalDateTime.now());
            final var foundUser = databaseStrategy.getDatabase().get(UUID.fromString(user.getId()));
            if (foundUser == null) {
                throw new RuntimeException("User not found");
            }

            foundUser.setStatus(user.getStatus());
            foundUser.setFirstName(user.getFirstName());
            foundUser.setLastName(user.getLastName());
            foundUser.setUpdate(LocalDateTime.now());
            mysqlDao.update(foundUser);
            elasticDao.update(foundUser);

            return foundUser;
        } catch (Exception e) {
            throw new RuntimeException("It was not persisted in the database", e);
        }

    }

    @Override
    public User get(String id) {
        return databaseStrategy.getDatabase().get(UUID.fromString(id));
    }

    @Override
    public void delete(String id) {

        try {
            final var uuid = UUID.fromString(id);
            mysqlDao.delete(uuid);
            elasticDao.delete(uuid);
        } catch (Exception e) {
            throw new RuntimeException("It was not deleted in the database", e);
        }
    }

    @Override
    public List<User> findAll() {
        return databaseStrategy.getDatabase().findAll();
    }
}

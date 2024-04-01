package com.vicarius.quota.dao.impl;

import com.vicarius.quota.dao.UserRepository;
import com.vicarius.quota.dao.elastic.ElasticDaoImpl;
import com.vicarius.quota.dao.mysql.MySqlDaoImpl;
import com.vicarius.quota.dao.strategy.DatabaseStrategy;
import com.vicarius.quota.dao.strategy.UserDao;
import com.vicarius.quota.exceptions.UnavailableErrorException;
import com.vicarius.quota.model.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final DatabaseStrategy databaseStrategy;

    private final UserDao mysqlDao;

    private final UserDao elasticDao;

    public UserRepositoryImpl(DatabaseStrategy databaseStrategy, @Qualifier(MySqlDaoImpl.IMPLEMENTATION_ID) UserDao mysqlDao,
                              @Qualifier(ElasticDaoImpl.IMPLEMENTATION_ID) UserDao elasticDao) {
        this.databaseStrategy = databaseStrategy;
        this.mysqlDao = mysqlDao;
        this.elasticDao = elasticDao;
    }

    @Override
    @Transactional
    public User save(User user) {
        try {
            final var persistedUser = mysqlDao.save(user);
            elasticDao.save(persistedUser);

            return persistedUser;
        } catch (Exception e) {
            throw new UnavailableErrorException("There was a problem to save data in the database", e);
        }
    }

    @Override
    public User get(UUID id) {
        return databaseStrategy.getDatabase().get(id);
    }

    @Override
    @Transactional
    public User update(User user) {
        try {
            final var persistedUser = mysqlDao.update(user);
            elasticDao.update(persistedUser);
            return persistedUser;
        } catch (Exception e) {
            throw new UnavailableErrorException("There was a problem to update data in the database", e);
        }
    }

    @Override
    public void delete(UUID id) {
        try{
            mysqlDao.delete(id);
            elasticDao.delete(id);
        } catch (Exception e) {
            throw new UnavailableErrorException("There was a problem to delete data in the database", e);
        }
    }

    @Override
    public List<User> findAll() {
        return databaseStrategy.getDatabase().findAll();
    }
}

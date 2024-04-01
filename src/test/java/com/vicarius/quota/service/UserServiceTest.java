package com.vicarius.quota.service;

import com.vicarius.quota.model.User;
import com.vicarius.quota.dao.strategy.DatabaseStrategy;
import com.vicarius.quota.dao.strategy.UserDao;
import com.vicarius.quota.services.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserServiceTest {

    @Mock
    private DatabaseStrategy databaseStrategy;

    @Mock
    private UserDao mysqlDao;

    @Mock
    private UserDao elasticDao;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void beforeEach() {
        userService = new UserServiceImpl(databaseStrategy, mysqlDao, elasticDao);
    }

    @Test
    void create_servicesUp_dataWasSaved() {
        User user = User.builder().firstName("First").lastName("Last").build();
        Mockito.when(mysqlDao.save(user)).thenReturn(user);

        userService.create(user);

        Mockito.verify(elasticDao, Mockito.times(1)).save(user);
    }

    @Test
    void create_MySqlDown_dataWasSaved() {
        User user = User.builder().firstName("First").lastName("Last").build();
        Mockito.when(mysqlDao.save(user)).thenReturn(user);

        userService.create(user);

        Mockito.verify(elasticDao, Mockito.times(1)).save(user);
    }
}

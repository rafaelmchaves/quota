package com.vicarius.quota.dao.strategy;

import com.vicarius.quota.dao.elastic.ElasticDaoImpl;
import com.vicarius.quota.dao.mysql.MySqlDaoImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
public class DatabaseStrategyTest {

    @InjectMocks
    private DatabaseStrategy databaseStrategy;

    @Mock
    private Map<String, UserDao> userBoundaryMap;

    @Test
    void getDatabase_timeDuringTheDay_returnMySqlImplementation() {
        final var now = LocalDateTime.now();

        ReflectionTestUtils.setField(databaseStrategy, "dayStartTime", LocalTime.of(now.getHour() ,now.getMinute(),now.getSecond() + -1));
        ReflectionTestUtils.setField(databaseStrategy, "dayEndTime", LocalTime.of(now.getHour() ,now.getMinute(),now.getSecond() + 1));
        databaseStrategy.getDatabase();

        Mockito.verify(userBoundaryMap, Mockito.times(1)).get(MySqlDaoImpl.IMPLEMENTATION_ID);
        Mockito.verify(userBoundaryMap, Mockito.times(0)).get(ElasticDaoImpl.IMPLEMENTATION_ID);
    }

    @Test
    void getDatabase_timeDuringTheNight_returnElasticImplementation() {
        final var now = LocalDateTime.now();

        ReflectionTestUtils.setField(databaseStrategy, "dayStartTime", LocalTime.of(now.getHour() ,now.getMinute(),now.getSecond() + 1));
        ReflectionTestUtils.setField(databaseStrategy, "dayEndTime", LocalTime.of(now.getHour() ,now.getMinute(),now.getSecond() + -1));
        databaseStrategy.getDatabase();

        Mockito.verify(userBoundaryMap, Mockito.times(0)).get(MySqlDaoImpl.IMPLEMENTATION_ID);
        Mockito.verify(userBoundaryMap, Mockito.times(1)).get(ElasticDaoImpl.IMPLEMENTATION_ID);
    }


}

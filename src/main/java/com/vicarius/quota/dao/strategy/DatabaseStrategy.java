package com.vicarius.quota.dao.strategy;

import com.vicarius.quota.dao.elastic.ElasticDaoImpl;
import com.vicarius.quota.dao.mysql.MySqlDaoImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DatabaseStrategy {

    private final Map<String, UserDao> userBoundaryMap;

    //It can't be final because of reflections that was set in the unit tests
    private LocalTime dayStartTime= LocalTime.of(8,0,0);
    private LocalTime dayEndTime = LocalTime.of(23,0,0);

    public UserDao getDatabase() {
        final var now = LocalTime.now();
        if (now.isAfter(dayStartTime)
                && now.isBefore(dayEndTime)) {
            return userBoundaryMap.get(MySqlDaoImpl.IMPLEMENTATION_ID);
        }

        return userBoundaryMap.get(ElasticDaoImpl.IMPLEMENTATION_ID);
    }
}
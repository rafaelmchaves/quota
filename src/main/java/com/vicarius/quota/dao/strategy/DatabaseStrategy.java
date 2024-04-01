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

    public UserDao getDatabase() {
        final var now = LocalTime.now();
        if (now.isAfter(LocalTime.of(8,0,0))
                && now.isBefore(LocalTime.of(23, 0,0))) {
            return userBoundaryMap.get(MySqlDaoImpl.IMPLEMENTATION_ID);
        }

        return userBoundaryMap.get(ElasticDaoImpl.IMPLEMENTATION_ID);
    }
}
package com.vicarius.quota.repository;

import com.vicarius.quota.repository.elastic.ElasticImplementation;
import com.vicarius.quota.repository.mysql.MySqlImplementation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DatabaseStrategy {

    private final Map<String, UserBoundary> userBoundaryMap;

    public UserBoundary getDatabase() {

        final var now = LocalTime.now();
        if (now.isAfter(LocalTime.of(8,0,0))
                && now.isBefore(LocalTime.of(23, 0,0))) {
            return userBoundaryMap.get(MySqlImplementation.IMPLEMENTATION_ID);
        }

        return userBoundaryMap.get(ElasticImplementation.IMPLEMENTATION_ID);
    }
}
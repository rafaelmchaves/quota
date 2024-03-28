package com.vicarius.quota.repository;

import com.vicarius.quota.repository.elastic.ElasticImplementation;
import com.vicarius.quota.repository.mysql.MySqlImplementation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DatabaseFactory {

    private final Map<String, DatabaseInterface> databaseInterfaceMap;

    public DatabaseInterface getDatabase() {

        final var now = LocalTime.now();
        if (now.isAfter(LocalTime.of(9,0,0))
                && now.isBefore(LocalTime.of(17, 0,0))) {
            return databaseInterfaceMap.get(MySqlImplementation.IMPLEMENTATION_ID);
        }

        return databaseInterfaceMap.get(ElasticImplementation.IMPLEMENTATION_ID);
    }
}
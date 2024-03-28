package com.vicarius.quota.repository;

import com.vicarius.quota.repository.DatabaseInterface;
import com.vicarius.quota.repository.elastic.ElasticImplementation;
import com.vicarius.quota.repository.mysql.MySqlImplementation;

import java.time.LocalTime;

public class DatabaseFactory {
    public DatabaseInterface getDatabase() {

        final var now = LocalTime.now();
        if (now.isAfter(LocalTime.of(9,0,0))
                && now.isBefore(LocalTime.of(17, 0,0))) {
            return new MySqlImplementation();
        }

        return new ElasticImplementation();
    }
}
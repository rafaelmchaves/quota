package com.vicarius.quota.repository.elastic;

import com.vicarius.quota.model.User;
import com.vicarius.quota.repository.DatabaseInterface;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ElasticImplementation implements DatabaseInterface {
    @Override
    public User save(User user) {

        log.info("User was saved");
        return null;
    }
}

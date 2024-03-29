package com.vicarius.quota.repository.elastic;

import com.vicarius.quota.model.User;
import com.vicarius.quota.repository.DatabaseInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service(ElasticImplementation.IMPLEMENTATION_ID)
public class ElasticImplementation implements DatabaseInterface {
    public static final String IMPLEMENTATION_ID = "ElasticImplementation";

    @Override
    public User save(User user) {

        log.info("User was saved");
        return null;
    }

    @Override
    public User get(UUID id) {
        return null;
    }

    @Override
    public User update(User user) {
        return null;
    }

    @Override
    public User delete(UUID id) {
        return null;
    }
}

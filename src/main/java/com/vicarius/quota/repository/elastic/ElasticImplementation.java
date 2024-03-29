package com.vicarius.quota.repository.elastic;

import com.vicarius.quota.model.User;
import com.vicarius.quota.repository.UserBoundary;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service(ElasticImplementation.IMPLEMENTATION_ID)
public class ElasticImplementation implements UserBoundary {
    public static final String IMPLEMENTATION_ID = "ElasticImplementation";

    @Override
    public User save(User user) {

        log.info("User was saved");
        throw new RuntimeException("Elastic problem");
    }

    @Override
    public User get(UUID id) {
        log.info("User was found");
        return null;
    }

    @Override
    public User update(User user) {
        log.info("User was updated");
        return null;
    }

    @Override
    public void delete(UUID id) {
        log.info("User was deleted");
    }
}

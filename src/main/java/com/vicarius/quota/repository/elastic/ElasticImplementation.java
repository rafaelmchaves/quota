package com.vicarius.quota.repository.elastic;

import com.vicarius.quota.model.User;
import com.vicarius.quota.repository.UserBoundary;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service(ElasticImplementation.IMPLEMENTATION_ID)
public class ElasticImplementation implements UserBoundary {

    private final Map<String, User> registers = new HashMap<>();

    public static final String IMPLEMENTATION_ID = "ElasticImplementation";

    @Override
    public User save(User user) {
        registers.put(user.getId(), user);
        return user;
    }

    @Override
    public User get(UUID id) {
        return registers.get(id.toString());
    }

    @Override
    public User update(User user) {
        return save(user);
    }

    @Override
    public void delete(UUID id) {
        registers.remove(id.toString());
    }

    @Override
    public List<User> findAll() {
        return registers.values().stream().toList();
    }
}

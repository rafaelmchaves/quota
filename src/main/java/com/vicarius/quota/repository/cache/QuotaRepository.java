package com.vicarius.quota.repository.cache;

import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class QuotaRepository {

    private final Map<String, Integer> quota = new HashMap<>();

    @CachePut("quota")
    public Integer sumQuota(String userId) {
        if (quota.containsKey(userId)) {
            quota.put(userId, quota.get(userId) + 1);
        } else {
            quota.put(userId, 1);
        }

        return quota.get(userId);

    }

    @Cacheable("quota")
    public Integer getQuota(String userId) {
        return quota.get(userId);
    }

}

package com.vicarius.quota.dao.impl;

import com.vicarius.quota.dao.QuotaRepository;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class QuotaRepositoryImpl implements QuotaRepository {

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
        Integer quotaAmount = quota.get(userId);
        return quotaAmount == null ? 0 : quotaAmount;
    }

}

package com.vicarius.quota.dao.impl;

import com.vicarius.quota.dao.QuotaRepository;
import com.vicarius.quota.dao.redis.QuotaEntity;
import com.vicarius.quota.dao.redis.QuotaRedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;


@RequiredArgsConstructor
@Component
public class QuotaRepositoryImpl implements QuotaRepository {

    private final QuotaRedisRepository quotaRedisRepository;

    @CachePut("quota")
    public Integer sumQuota(String userId) {
        final var quota = quotaRedisRepository.findById(userId)
                .orElse(QuotaEntity.builder().usedQuota(0).id(userId).build());
        quota.setUsedQuota(quota.getUsedQuota() + 1);
        quotaRedisRepository.save(quota);
        
        return quota.getUsedQuota();
    }

    @Cacheable("quota")
    public Integer getQuota(String userId) {
        final var quota = quotaRedisRepository.findById(userId);
        return quota.map(QuotaEntity::getUsedQuota).orElse(0);
    }

}

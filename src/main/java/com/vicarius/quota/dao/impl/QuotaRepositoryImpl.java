package com.vicarius.quota.dao.impl;

import com.vicarius.quota.dao.QuotaRepository;
import com.vicarius.quota.dao.redis.QuotaEntity;
import com.vicarius.quota.dao.redis.QuotaRedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@RequiredArgsConstructor
@Component
public class QuotaRepositoryImpl implements QuotaRepository {

    private final QuotaRedisRepository quotaRedisRepository;

    public Integer sumQuota(String userId) {
        final var quota = quotaRedisRepository.findById(userId)
                .orElse(QuotaEntity.builder().usedQuota(0).id(userId).build());
        int usedQuota = quota.getUsedQuota() + 1;
        quota.setUsedQuota(usedQuota);
        quotaRedisRepository.save(quota);

        return usedQuota;
    }

    public Integer getQuota(String userId) {
        final var quota = quotaRedisRepository.findById(userId);
        return quota.map(QuotaEntity::getUsedQuota).orElse(0);
    }

}

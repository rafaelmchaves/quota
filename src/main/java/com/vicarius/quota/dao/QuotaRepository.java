package com.vicarius.quota.dao;

import com.vicarius.quota.dao.redis.QuotaEntity;
import org.springframework.stereotype.Component;


@Component
public interface QuotaRepository {

    Integer sumQuota(String userId);

    Integer getQuota(String userId);

}

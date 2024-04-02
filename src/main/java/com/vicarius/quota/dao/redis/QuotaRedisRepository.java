package com.vicarius.quota.dao.redis;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuotaRedisRepository extends CrudRepository<QuotaEntity, String> {
}

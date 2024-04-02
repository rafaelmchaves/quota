package com.vicarius.quota.dao.redis;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;

@Builder
@Getter
@Setter
@RedisHash("UserQuota")
public class QuotaEntity {

    private String id;

    private Integer usedQuota;

}

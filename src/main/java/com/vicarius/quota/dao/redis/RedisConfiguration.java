package com.vicarius.quota.dao.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;

@Configuration
public class RedisConfiguration {

    @Bean
    public Jedis jedis(@Value("${spring.redis.host}") final String host,
                       @Value("${spring.redis.port}") final int port) {
        return new Jedis(host, port);
    }
}

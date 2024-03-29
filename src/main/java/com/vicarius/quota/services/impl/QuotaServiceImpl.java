package com.vicarius.quota.services.impl;

import com.vicarius.quota.repository.DatabaseStrategy;
import com.vicarius.quota.services.QuotaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class QuotaServiceImpl implements QuotaService {

    private final DatabaseStrategy databaseStrategy;

    @Override
    public void consumeQuota(String userId) {
        final var user = this.databaseStrategy.getDatabase().get(UUID.fromString(userId));
        if (user == null) {
            throw new RuntimeException("User not found");
        }



    }
}

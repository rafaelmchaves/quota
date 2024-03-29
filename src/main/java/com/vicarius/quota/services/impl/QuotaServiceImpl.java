package com.vicarius.quota.services.impl;

import com.vicarius.quota.repository.DatabaseStrategy;
import com.vicarius.quota.repository.cache.QuotaRepository;
import com.vicarius.quota.services.QuotaService;
import com.vicarius.quota.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class QuotaServiceImpl implements QuotaService {

    private static final Integer REQUESTS_PER_USER = 5;

    private final DatabaseStrategy databaseStrategy;

    private final QuotaRepository quotaRepository;

    private final UserService userService;

    @Override
    public void consumeQuota(String userId) {
        final var user = this.databaseStrategy.getDatabase().get(UUID.fromString(userId));
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        quotaRepository.sumQuota(userId);

        if (quotaRepository.getQuota(userId) > REQUESTS_PER_USER) {
            userService.blockUser(user);
        }

    }
}

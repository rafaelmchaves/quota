package com.vicarius.quota.services.impl;

import com.vicarius.quota.exceptions.UnavailableErrorException;
import com.vicarius.quota.exceptions.UserNotFoundException;
import com.vicarius.quota.model.User;
import com.vicarius.quota.model.UserQuota;
import com.vicarius.quota.exceptions.MaximumQuotaException;
import com.vicarius.quota.dao.QuotaRepository;
import com.vicarius.quota.services.QuotaService;
import com.vicarius.quota.services.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuotaServiceImpl implements QuotaService {

    @Value("${REQUESTS_PER_USER}")
    private Integer requestsPerUser;

    private final QuotaRepository quotaRepository;

    private final UserService userService;

    @Transactional
    @Override
    public void consumeQuota(String userId) {
        final var user = this.userService.get(userId);
        if (user == null) {
            throw new UserNotFoundException("User not found");
        }

        int quota = quotaRepository.getQuota(user.getId());
        if (quota + 1 > requestsPerUser) {
            throw new MaximumQuotaException("You have used the maximum quotas allowed. Max allowed: " + requestsPerUser);
        }

        try {
            quota = quotaRepository.sumQuota(userId);
            user.setLastConsumeTimeUtc(LocalDateTime.now(ZoneOffset.UTC));
            this.userService.update(user);

            if (quota == requestsPerUser) {
                userService.lockUser(user);
            }
        } catch (Exception ex) {
            throw new UnavailableErrorException("It was not possible to update user", ex);
        }

    }

    public List<UserQuota> getAll() {
        final var users = userService.findAll();
        return users.stream().map(this::getUserQuota).toList();
    }

    private UserQuota getUserQuota(User user) {
        return UserQuota.builder()
                .usedQuota(quotaRepository.getQuota(user.getId()))
                .user(User.builder().id(user.getId())
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .status(user.getStatus()).build()).build();
    }
}

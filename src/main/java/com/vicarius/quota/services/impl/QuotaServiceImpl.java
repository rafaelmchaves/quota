package com.vicarius.quota.services.impl;

import com.vicarius.quota.model.User;
import com.vicarius.quota.model.UserQuota;
import com.vicarius.quota.repository.cache.QuotaRepository;
import com.vicarius.quota.services.QuotaService;
import com.vicarius.quota.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuotaServiceImpl implements QuotaService {

    private static final Integer REQUESTS_PER_USER = 5;

    private final QuotaRepository quotaRepository;

    private final UserService userService;

    @Override
    public void consumeQuota(String userId) {
        final var user = this.userService.get(userId);
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        int quota = quotaRepository.getQuota(user.getId());
        if (quota > REQUESTS_PER_USER) {
            throw new RuntimeException("You used your maximum quota");
        }

        quota = quotaRepository.sumQuota(userId);
        if (quota > REQUESTS_PER_USER) {
            userService.lockUser(user);
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

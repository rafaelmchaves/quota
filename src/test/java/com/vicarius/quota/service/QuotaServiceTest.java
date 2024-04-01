package com.vicarius.quota.service;

import com.vicarius.quota.exceptions.ErrorCode;
import com.vicarius.quota.exceptions.MaximumQuotaException;
import com.vicarius.quota.model.Status;
import com.vicarius.quota.model.User;
import com.vicarius.quota.repository.cache.QuotaRepository;
import com.vicarius.quota.services.UserService;
import com.vicarius.quota.services.impl.QuotaServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

@SpringBootTest
class QuotaServiceTest {

    @InjectMocks
    private QuotaServiceImpl quotaService;

    @Mock
    private QuotaRepository quotaRepository;

    @Mock
    private UserService userService;

    @BeforeEach
    void beforeEach() {
        ReflectionTestUtils.setField(quotaService, "requestsPerUser", 5);
    }

    @Test
    void consumeQuota_firstConsume_noExceptionAndNoLock() {

        String userId = "uuid";
        final var user = getUser(userId);
        Mockito.when(userService.get(userId)).thenReturn(user);
        Mockito.when(quotaRepository.getQuota(userId)).thenReturn(0);
        Mockito.when(quotaRepository.sumQuota(userId)).thenReturn(1);

        quotaService.consumeQuota(userId);

        Mockito.verify(userService, Mockito.times(0)).lockUser(user);
    }

    @Test
    void consumeQuota_fifthConsume_lockUser() {

        String userId = "uuid";
        final var user = getUser(userId);
        Mockito.when(userService.get(userId)).thenReturn(user);
        Mockito.when(quotaRepository.getQuota(userId)).thenReturn(4);
        Mockito.when(quotaRepository.sumQuota(userId)).thenReturn(5);

        quotaService.consumeQuota(userId);

        Mockito.verify(userService, Mockito.times(1)).lockUser(user);
    }

    @Test
    void consumeQuota_sixthConsume_throwAnExceptionBecauseOfMaximumQuotaExceeded() {

        String userId = "uuid";
        final var user = getUser(userId);
        Mockito.when(userService.get(userId)).thenReturn(user);
        Mockito.when(quotaRepository.getQuota(userId)).thenReturn(5);

        final var exception = Assertions.assertThrows(MaximumQuotaException.class, () -> quotaService.consumeQuota(userId));

        Assertions.assertEquals("You have used the maximum quotas allowed. Max allowed: 5", exception.getMessage());
        Assertions.assertEquals(ErrorCode.MAXIMUM_QUOTA.getCode(), exception.getCode());

        Mockito.verify(userService, Mockito.times(0)).lockUser(user);
        Mockito.verify(quotaRepository, Mockito.times(0)).sumQuota(userId);

    }

    private User getUser(String userId) {
        return User.builder().id(userId).firstName("Rafael").lastName("Chaves").status(Status.ACTIVE).build();
    }
}

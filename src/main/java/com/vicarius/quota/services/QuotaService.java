package com.vicarius.quota.services;

import com.vicarius.quota.model.UserQuota;

import java.util.List;

public interface QuotaService {

    void consumeQuota(String userId);

    List<UserQuota> getAll();

}

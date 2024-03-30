package com.vicarius.quota.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class UserQuota {

    private Integer usedQuota;
    private User user;

}

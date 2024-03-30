package com.vicarius.quota.controller.response;

import com.vicarius.quota.model.Status;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuotaResponse {

    private Integer usedQuota;
    private String id;
    private String firstName;
    private String lastName;
    private Status status;
}

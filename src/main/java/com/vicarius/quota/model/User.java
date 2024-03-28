package com.vicarius.quota.model;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class User {

    private String id;
    private String firstName;
    private String lastName;
    private LocalDateTime lastLoginTimeUtc;
    private LocalDateTime creation;
    private LocalDateTime update;
    private Status status;

}

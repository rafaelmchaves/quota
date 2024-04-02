package com.vicarius.quota.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
public class User {

    private String id;
    private String firstName;
    private String lastName;
    private LocalDateTime lastConsumeTimeUtc;
    private LocalDateTime creation;
    private LocalDateTime update;
    private Status status;

}

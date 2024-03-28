package com.vicarius.quota.controller.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

//TODO maybe create as Record
@Setter
@Getter
public class UserResponse {

    private String id;
    private String firstName;
    private String lastName;
    private LocalDateTime lastLoginTimeUtc;

}
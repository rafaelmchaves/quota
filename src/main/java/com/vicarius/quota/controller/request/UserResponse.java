package com.vicarius.quota.controller.request;

import com.vicarius.quota.model.Status;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

//TODO maybe create as a Record
@Setter
@Getter
public class UserResponse {

    private String id;
    private String firstName;
    private String lastName;
    private LocalDateTime lastLoginTimeUtc;
    private LocalDateTime creation;
    private Status status;

}

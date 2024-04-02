package com.vicarius.quota.controller.response;

import com.vicarius.quota.model.Status;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class UserResponse {

    private String id;
    private String firstName;
    private String lastName;
    private LocalDateTime lastConsumeTimeUtc;
    private LocalDateTime creation;
    private LocalDateTime update;
    private Status status;

}

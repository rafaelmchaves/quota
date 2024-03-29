package com.vicarius.quota.controller.request;

import com.vicarius.quota.model.Status;
import lombok.Getter;
import lombok.Setter;

//TODO maybe create as a Record
@Setter
@Getter
public class UserUpdateRequest {

    private String firstName;
    private String lastName;
    private Status status;

}

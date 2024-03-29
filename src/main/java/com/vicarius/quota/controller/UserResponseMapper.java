package com.vicarius.quota.controller;

import com.vicarius.quota.controller.response.UserResponse;
import com.vicarius.quota.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserResponseMapper {

    UserResponse convert(User user);
}

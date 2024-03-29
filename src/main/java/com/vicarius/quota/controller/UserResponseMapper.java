package com.vicarius.quota.controller;

import com.vicarius.quota.controller.response.UserResponse;
import com.vicarius.quota.model.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserResponseMapper {

    UserResponse convert(User user);

    List<UserResponse> convert(List<User> users);
}

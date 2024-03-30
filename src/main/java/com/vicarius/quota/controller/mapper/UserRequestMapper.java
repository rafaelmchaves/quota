package com.vicarius.quota.controller.mapper;

import com.vicarius.quota.controller.request.UserRequest;
import com.vicarius.quota.controller.request.UserUpdateRequest;
import com.vicarius.quota.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserRequestMapper {

    User convert(UserRequest user);

    User convert(UserUpdateRequest userUpdateRequest);
}

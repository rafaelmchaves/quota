package com.vicarius.quota.dao.mysql;

import com.vicarius.quota.model.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserEntityMapper {

    User convert(UserEntity userEntity);
    UserEntity convert(User userEntity);

    List<User> convert(List<UserEntity> users);

}

package com.vicarius.quota.repository.mysql;

import com.vicarius.quota.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserEntityMapper {

    User convert(UserEntity userEntity);
    UserEntity convert(User userEntity);

}

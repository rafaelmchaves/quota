package com.vicarius.quota.controller.mapper;

import com.vicarius.quota.controller.response.QuotaResponse;
import com.vicarius.quota.model.UserQuota;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface QuotaMapper {

    @Mappings({
            @Mapping(target = "id", source = "user.id"),
            @Mapping(target = "firstName", source = "user.firstName"),
            @Mapping(target = "lastName", source = "user.lastName"),
            @Mapping(target = "status", source = "user.status")
    })
    QuotaResponse convert(UserQuota userQuota);

    List<QuotaResponse> convert(List<UserQuota> userQuota);

}

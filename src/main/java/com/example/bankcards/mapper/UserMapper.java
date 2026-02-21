package com.example.bankcards.mapper;

import com.bank_rest.model.RegistrationRequest;
import com.example.bankcards.domain.UserDomain;
import com.example.bankcards.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = RoleMapper.class)
public interface UserMapper {

    @Mapping(target = "isBlocked", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "id", ignore = true)
    UserDomain toDomain(RegistrationRequest dto);

    UserEntity toEntity(UserDomain domain);

    UserDomain toDomain(UserEntity entity);
}

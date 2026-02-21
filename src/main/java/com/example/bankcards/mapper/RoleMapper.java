package com.example.bankcards.mapper;

import com.example.bankcards.domain.RoleDomain;
import com.example.bankcards.entity.RoleEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    RoleDomain toDomain(RoleEntity roleEntity);

    RoleEntity toEntity(RoleDomain roleDomain);
}

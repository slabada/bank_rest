package com.example.bankcards.service;

import com.example.bankcards.domain.RoleDomain;

public interface RoleService {

    RoleDomain findRoleByName(String name);
}

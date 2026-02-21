package com.example.bankcards.service;

import com.example.bankcards.domain.RoleDomain;
import com.example.bankcards.entity.RoleEntity;
import com.example.bankcards.enums.RoleEnum;
import com.example.bankcards.exception.RoleException;
import com.example.bankcards.mapper.RoleMapper;
import com.example.bankcards.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    @Override
    @Transactional(readOnly = true)
    public RoleDomain findRoleByName(String name) {
        RoleEntity role = roleRepository.findByName(RoleEnum.USER.getName())
                .orElseThrow(RoleException.NotFoundRoleException::new);
        return roleMapper.toDomain(role);
    }
}

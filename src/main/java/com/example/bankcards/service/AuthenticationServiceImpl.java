package com.example.bankcards.service;

import com.bank_rest.model.LoginRequest;
import com.example.bankcards.domain.RoleDomain;
import com.example.bankcards.domain.UserDomain;
import com.example.bankcards.entity.RoleEntity;
import com.example.bankcards.entity.UserEntity;
import com.example.bankcards.enums.RoleEnum;
import com.example.bankcards.mapper.RoleMapper;
import com.example.bankcards.mapper.UserMapper;
import com.example.bankcards.security.UserSecurity;
import com.example.bankcards.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserService userService;
    private final RoleService roleService;

    private final UserMapper userMapper;
    private final RoleMapper roleMapper;

    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Override
    @Transactional
    public String registration(UserDomain userDomain) {
        RoleDomain roleDomain = roleService.findRoleByName(RoleEnum.USER.getName());
        RoleEntity roleEntity = roleMapper.toEntity(roleDomain);
        String hashPassword = passwordEncoder.encode(userDomain.getPassword());
        UserEntity entity = UserEntity.builder()
                .firstName(userDomain.getFirstName())
                .lastName(userDomain.getLastName())
                .email(userDomain.getEmail())
                .password(hashPassword)
                .role(Set.of(roleEntity))
                .build();
        UserDomain domain = userMapper.toDomain(entity);
        UserDomain save = userService.createUser(domain);
        return jwtUtil.generateToken(save.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public String login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getLogin(),
                        loginRequest.getPassword()
                )
        );
        UserSecurity userSecurity = (UserSecurity) authentication.getPrincipal();
        return jwtUtil.generateToken(userSecurity.getId());
    }
}

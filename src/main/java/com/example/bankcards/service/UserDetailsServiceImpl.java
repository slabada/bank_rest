package com.example.bankcards.service;

import com.example.bankcards.domain.UserDomain;
import com.example.bankcards.entity.UserEntity;
import com.example.bankcards.exception.UserException;
import com.example.bankcards.mapper.UserMapper;
import com.example.bankcards.repository.UserRepository;
import com.example.bankcards.security.UserSecurity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(UserException.NotFoundUserException::new);
        Set<GrantedAuthority> roles = user.getRole().stream()
                .map(r -> new SimpleGrantedAuthority(r.getName()))
                .collect(Collectors.toSet());
        UserDomain domain = userMapper.toDomain(user);
        return UserSecurity.builder()
                .user(domain)
                .authorities(roles)
                .build();
    }

    @Transactional
    public UserDetails loadUserById(Long id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(UserException.NotFoundUserException::new);
        return buildUserDetails(user);
    }

    private UserDetails buildUserDetails(UserEntity user) {
        Set<GrantedAuthority> roles = user.getRole().stream()
                .map(r -> new SimpleGrantedAuthority(r.getName()))
                .collect(Collectors.toSet());
        UserDomain domain = userMapper.toDomain(user);
        return UserSecurity.builder()
                .user(domain)
                .authorities(roles)
                .build();
    }
}

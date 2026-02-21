package com.example.bankcards.security;

import com.example.bankcards.domain.UserDomain;
import lombok.Builder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * Реализация паттерна адаптер для адаптации {@link UserDomain} под {@link UserSecurity}
 */
@Builder(toBuilder = true)
public class UserSecurity implements UserDetails {

    private final UserDomain user;

    private Collection<? extends GrantedAuthority> authorities;

    public Long getId() {
        return user.getId();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isEnabled() {
        return !user.getIsBlocked();
    }
}

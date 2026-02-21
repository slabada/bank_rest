package com.example.bankcards.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Set;

@Getter
@AllArgsConstructor
public class UserDomain {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Set<RoleDomain> role;
    private Boolean isBlocked;
}

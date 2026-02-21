package com.example.bankcards.service;

import com.bank_rest.model.UserBlockedDto;
import com.example.bankcards.domain.UserDomain;

import java.util.List;
import java.util.Set;

public interface UserService {

    UserDomain createUser(UserDomain userDomain);

    List<UserDomain> findUserByIdAll(Set<Long> userIds);

    Long userBlocked(UserBlockedDto userBlockedDto);

    UserDomain findUserById(Long userId);
}

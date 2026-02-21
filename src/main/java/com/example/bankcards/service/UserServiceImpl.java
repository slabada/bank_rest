package com.example.bankcards.service;

import com.bank_rest.model.UserBlockedDto;
import com.example.bankcards.domain.UserDomain;
import com.example.bankcards.entity.UserEntity;
import com.example.bankcards.exception.UserException;
import com.example.bankcards.mapper.UserMapper;
import com.example.bankcards.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public UserDomain createUser(UserDomain userDomain) {
        boolean existsByEmail = userRepository.existsByEmail(userDomain.getEmail());
        if(existsByEmail) {
            throw new UserException.ConflictUserEmailException();
        }
        UserEntity entity = userMapper.toEntity(userDomain);
        UserEntity save = userRepository.save(entity);
        return userMapper.toDomain(save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDomain> findUserByIdAll(Set<Long> userIds) {
        List<UserEntity> users = userRepository.findAllById(userIds);
        return users.stream()
                .map(userMapper::toDomain)
                .toList();
    }

    @Override
    @Transactional
    public Long userBlocked(UserBlockedDto userBlockedDto) {
        UserEntity entity = userRepository.findById(userBlockedDto.getId())
                .orElseThrow(UserException.NotFoundUserException::new);
        entity.setIsBlocked(userBlockedDto.getBlock());
        userRepository.save(entity);
        return entity.getId();
    }

    @Override
    @Transactional(readOnly = true)
    public UserDomain findUserById(Long userId) {
        UserEntity entity = userRepository.findById(userId)
                .orElseThrow(UserException.NotFoundUserException::new);
        return userMapper.toDomain(entity);
    }
}

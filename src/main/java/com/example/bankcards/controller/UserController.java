package com.example.bankcards.controller;

import com.bank_rest.api.UserApi;
import com.bank_rest.model.ResponseId;
import com.bank_rest.model.UserBlockedDto;
import com.example.bankcards.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController implements UserApi {

    private final UserService userService;

    @Override
    public ResponseEntity<ResponseId> userBlocked(UserBlockedDto userBlockedDto) {
        Long userBlockedId = userService.userBlocked(userBlockedDto);
        return ResponseEntity.ok().body(new ResponseId().id(userBlockedId));
    }
}

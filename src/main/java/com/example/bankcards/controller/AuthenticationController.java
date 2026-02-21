package com.example.bankcards.controller;

import com.bank_rest.api.AuthenticationApi;
import com.bank_rest.model.LoginRequest;
import com.bank_rest.model.RegistrationRequest;
import com.bank_rest.model.TokenDto;
import com.example.bankcards.domain.UserDomain;
import com.example.bankcards.mapper.UserMapper;
import com.example.bankcards.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthenticationController implements AuthenticationApi {

    private final AuthenticationService authenticationService;
    private final UserMapper userMapper;

    @Override
    public ResponseEntity<TokenDto> registration(RegistrationRequest registrationRequest) {
        UserDomain domain = userMapper.toDomain(registrationRequest);
        var jwt = authenticationService.registration(domain);
        return ResponseEntity.status(HttpStatus.CREATED).body(new TokenDto().jwt(jwt));
    }

    @Override
    public ResponseEntity<TokenDto> login(LoginRequest loginRequest) {
        var jwt = authenticationService.login(loginRequest);
        return ResponseEntity.ok().body(new TokenDto().jwt(jwt));
    }
}

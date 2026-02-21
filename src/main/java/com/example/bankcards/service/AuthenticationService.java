package com.example.bankcards.service;

import com.bank_rest.model.LoginRequest;
import com.example.bankcards.domain.UserDomain;

public interface AuthenticationService {

    /**
     * Создание нового пользователя
     *
     * @param userDomain {@link UserDomain} объект содержащий данные для создания пользователя
     * @return {@link String} JWT-токен
     */
    String registration(UserDomain userDomain);

    /**
     * Метод для авторизации пользователя
     *
     * @param loginRequest {@link LoginRequest} Объект с данными для авторизации
     * @return {@link String} JWT-токен
     */
    String login(LoginRequest loginRequest);
}

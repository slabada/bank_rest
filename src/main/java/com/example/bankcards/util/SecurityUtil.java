package com.example.bankcards.util;

import com.example.bankcards.security.UserSecurity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtil {

    /**
     * Получить авторизованного пользователя
     *
     * @return {@link UserSecurity} Объект с информацие об авторизованном пользователе
     */
    public UserSecurity getUserSecurity(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AuthenticationCredentialsNotFoundException("Пользователь не аутентифицирован");
        }
        Object principal = authentication.getPrincipal();
        if (!(principal instanceof UserSecurity)) {
            throw new AuthenticationServiceException(
                    "Неподдерживаемый тип principal: " + principal.getClass().getName()
            );
        }
        return (UserSecurity) principal;
    }
}

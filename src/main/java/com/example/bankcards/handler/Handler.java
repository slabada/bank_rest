package com.example.bankcards.handler;

import com.bank_rest.model.ErrorResponse;
import com.example.bankcards.exception.AuthenticationException;
import com.example.bankcards.exception.CardException;
import com.example.bankcards.exception.RoleException;
import com.example.bankcards.exception.TranslationException;
import com.example.bankcards.exception.UserException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@RestControllerAdvice
public class Handler {

    /**
     * Not Found (404)
     *
     * @param ex исключение
     * @return кастомный объект исключения
     */
    @ExceptionHandler({
            RoleException.NotFoundRoleException.class,
            CardException.NotFoundCardException.class
    })
    public ResponseEntity<ErrorResponse> NotFound(Exception ex) {
        ErrorResponse error = new ErrorResponse()
                .message(ex.getMessage())
                .timestamp(OffsetDateTime.now(ZoneOffset.UTC))
                .code(HttpStatus.NOT_FOUND.value());
        return ResponseEntity.status(error.getCode()).body(error);
    }

    /**
     * Conflict (409)
     *
     * @param ex исключение
     * @return кастомный объект исключения
     */
    @ExceptionHandler({
            UserException.ConflictUserEmailException.class,
            TranslationException.ConflictNumberCardException.class
    })
    public ResponseEntity<ErrorResponse> Conflict(Exception ex) {
        ErrorResponse error = new ErrorResponse()
                .message(ex.getMessage())
                .timestamp(OffsetDateTime.now(ZoneOffset.UTC))
                .code(HttpStatus.CONFLICT.value());
        return ResponseEntity.status(error.getCode()).body(error);
    }

    /**
     * BadRequest (400)
     *
     * @param ex исключение
     * @return кастомный объект исключения
     */
    @ExceptionHandler({
            AuthenticationException.InvalidAuthenticationException.class,
            TranslationException.BadRequestCardStatusException.class,
            AuthenticationException.InsufficientFundsException.class
    })
    public ResponseEntity<ErrorResponse> BadRequest(Exception ex) {
        ErrorResponse error = new ErrorResponse()
                .message(ex.getMessage())
                .timestamp(OffsetDateTime.now(ZoneOffset.UTC))
                .code(HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(error.getCode()).body(error);
    }

    /**
     * InternalServerError (500)
     *
     * @param ex исключение
     * @return кастомный объект исключения
     */
    @ExceptionHandler({
            AuthenticationServiceException.class
    })
    public ResponseEntity<ErrorResponse> InternalServerError(Exception ex) {
        ErrorResponse error = new ErrorResponse()
                .message(ex.getMessage())
                .timestamp(OffsetDateTime.now(ZoneOffset.UTC))
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return ResponseEntity.status(error.getCode()).body(error);
    }
}

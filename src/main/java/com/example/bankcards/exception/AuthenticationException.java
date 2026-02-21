package com.example.bankcards.exception;

public class AuthenticationException {

    public static class InvalidAuthenticationException extends RuntimeException {
        public InvalidAuthenticationException() {
            super("Неверный логин или пароль");
        }
    }

    public static class InsufficientFundsException extends RuntimeException {
        public InsufficientFundsException(String message) {
            super(message);
        }
    }
}

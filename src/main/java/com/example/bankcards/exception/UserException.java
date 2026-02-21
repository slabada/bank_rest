package com.example.bankcards.exception;

public class UserException {

    public static class ConflictUserEmailException extends RuntimeException {
        public ConflictUserEmailException() {
            super("Пользователь с такой почтой уже существует");
        }
    }

    public static class NotFoundEmailException extends RuntimeException {
        public NotFoundEmailException() {
            super("Пользователь с данной почтой не найден");
        }
    }

    public static class NotFoundUserException extends RuntimeException {
        public NotFoundUserException() {
            super("Пользователь не найден");
        }
    }
}

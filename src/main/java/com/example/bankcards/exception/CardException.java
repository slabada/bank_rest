package com.example.bankcards.exception;

public class CardException {

    public static class NotFoundCardException extends RuntimeException {
        public NotFoundCardException() {
            super("Карта не найдена");
        }
    }
}

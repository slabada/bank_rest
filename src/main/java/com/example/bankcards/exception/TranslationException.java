package com.example.bankcards.exception;

public class TranslationException {

    public static class BadRequestCardStatusException extends RuntimeException {
        public BadRequestCardStatusException() {
            super("Карта не является активной");
        }
    }

    public static class ConflictNumberCardException extends RuntimeException {
        public ConflictNumberCardException() {
            super("Карта не является активной");
        }
    }
}

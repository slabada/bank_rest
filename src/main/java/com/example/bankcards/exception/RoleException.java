package com.example.bankcards.exception;

public class RoleException {

    public static class NotFoundRoleException extends RuntimeException {
        public NotFoundRoleException() {
            super("Роль не найдена");
        }
    }
}

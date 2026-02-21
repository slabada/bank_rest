package com.example.bankcards.enums;

import lombok.Getter;

@Getter
public enum StatusCardEnum {
    ACTION,
    BLOCK,
    EXPIRED;

    public static StatusCardEnum fromString(String status) {
        for (StatusCardEnum s : StatusCardEnum.values()) {
            if (s.name().equalsIgnoreCase(status)) {
                return s;
            }
        }
        throw new IllegalArgumentException("Unknown status: " + status);
    }
}

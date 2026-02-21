package com.example.bankcards.enums;

public enum CardBlockedStatus {
    PENDING,
    APPROVED,
    REJECTED;

    public static CardBlockedStatus fromString(String status) {
        for (CardBlockedStatus s : CardBlockedStatus.values()) {
            if (s.name().equalsIgnoreCase(status)) {
                return s;
            }
        }
        throw new IllegalArgumentException("Unknown status: " + status);
    }
}

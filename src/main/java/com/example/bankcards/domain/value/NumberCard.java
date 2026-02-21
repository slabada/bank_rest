package com.example.bankcards.domain.value;

import lombok.Getter;

@Getter
public class NumberCard {
    private String number;
    private String numberMask;

    public NumberCard(String number) {
        this.number = number;
        this.numberMask = mask(number);
    }

    private String mask(String numberMask) {
        return  "**** **** **** " + numberMask.substring(numberMask.length() - 4);
    }
}

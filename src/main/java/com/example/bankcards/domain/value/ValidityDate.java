package com.example.bankcards.domain.value;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ValidityDate {
    private Integer yearValidity;
    private Integer monthValidity;
}

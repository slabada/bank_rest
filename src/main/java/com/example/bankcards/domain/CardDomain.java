package com.example.bankcards.domain;

import com.example.bankcards.domain.value.Money;
import com.example.bankcards.domain.value.NumberCard;
import com.example.bankcards.domain.value.Owner;
import com.example.bankcards.domain.value.ValidityDate;
import com.example.bankcards.enums.StatusCardEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CardDomain {

    private Long id;
    private NumberCard numberCard;
    private Owner owner;
    private ValidityDate validityDate;
    private StatusCardEnum status;
    private Money balance;
}

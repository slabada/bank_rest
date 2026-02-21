package com.example.bankcards.domain.value;

import com.example.bankcards.exception.AuthenticationException;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Embeddable
public class Money {

    private BigDecimal amount;

    public void withdraw(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new AuthenticationException.InsufficientFundsException("Сумма должна быть положительной");
        }
        if (this.amount.compareTo(amount) < 0) {
            throw new AuthenticationException.InsufficientFundsException("Недостаточно средств");
        }
        this.amount = this.amount.subtract(amount);
    }

    public void deposit(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new AuthenticationException.InsufficientFundsException("Сумма должна быть положительной");
        }
        this.amount = this.amount.add(amount);
    }
}

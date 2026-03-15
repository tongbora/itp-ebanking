package com.tongbora.common.domain.valueobject;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Money {
    BigDecimal amount;
    Currency currency;


    public Money deposit(Money amount) {
        if (!this.currency.equals(amount.getCurrency())) {
            throw new IllegalArgumentException("Currency mismatch");
        }
        return new Money(this.amount.add(amount.getAmount()), this.currency);
    }

    public Money withdraw(Money amount){
        if (!this.currency.equals(amount.getCurrency())) {
            throw new IllegalArgumentException("Currency mismatch");
        }
        if (this.amount.compareTo(amount.getAmount()) < 0) {
            throw new IllegalArgumentException("Insufficient funds");
        }
        return new Money(this.amount.subtract(amount.getAmount()), this.currency);
    }

}

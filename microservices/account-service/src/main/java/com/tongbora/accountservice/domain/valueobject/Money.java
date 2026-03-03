package com.tongbora.accountservice.domain.valueobject;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class Money {
    BigDecimal amount;
    Currency currency;
}

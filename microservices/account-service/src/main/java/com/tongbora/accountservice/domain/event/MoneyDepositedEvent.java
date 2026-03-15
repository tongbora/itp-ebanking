package com.tongbora.accountservice.domain.event;

import com.tongbora.common.domain.valueobject.Money;
import com.tongbora.common.AccountId;
import com.tongbora.common.TransactionId;
import lombok.Builder;

import java.time.ZonedDateTime;


@Builder
public record MoneyDepositedEvent(
        AccountId accountId,
        TransactionId transactionId,
        Money amount,
        Money newBalance,
        String remark,
        ZonedDateTime createdAt
) {
}

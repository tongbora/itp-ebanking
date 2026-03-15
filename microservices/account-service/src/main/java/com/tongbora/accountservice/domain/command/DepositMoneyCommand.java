package com.tongbora.accountservice.domain.command;

import com.tongbora.common.domain.valueobject.Money;
import com.tongbora.common.AccountId;
import com.tongbora.common.TransactionId;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

public record DepositMoneyCommand(
        @TargetAggregateIdentifier
    AccountId accountId,
    TransactionId transactionId,
    Money amount,
    String remark
) {
}

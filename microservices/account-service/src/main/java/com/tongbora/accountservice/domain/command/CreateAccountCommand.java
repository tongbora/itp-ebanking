package com.tongbora.accountservice.domain.command;

import com.tongbora.common.domain.valueobject.AccountType;
import com.tongbora.common.domain.valueobject.Money;
import com.tongbora.common.AccountId;
import com.tongbora.common.BranchId;
import com.tongbora.common.CustomerId;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

public record CreateAccountCommand(
        @TargetAggregateIdentifier
        AccountId accountId,
        String accountNumber,
        String accountHolder,
        CustomerId customerId,
        AccountType accountType,
        BranchId branchId,
        Money initialBalance
) {
}

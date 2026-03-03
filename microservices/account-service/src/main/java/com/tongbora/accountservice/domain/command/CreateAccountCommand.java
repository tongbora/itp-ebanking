package com.tongbora.accountservice.domain.command;

import com.tongbora.accountservice.domain.valueobject.AccountType;
import com.tongbora.accountservice.domain.valueobject.Money;
import com.tongbora.common.AccountId;
import com.tongbora.common.BranchId;
import com.tongbora.common.CustomerId;

public record CreateAccountCommand(
        AccountId accountId,
        String accountNumber,
        String accountHolder,
        CustomerId customerId,
        AccountType accountType,
        BranchId branchId,
        Money initialBalance
) {
}

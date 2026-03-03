package com.tongbora.accountservice.application.dto.create;

import com.tongbora.accountservice.domain.valueobject.AccountType;
import com.tongbora.accountservice.domain.valueobject.Money;
import com.tongbora.common.AccountId;
import com.tongbora.common.BranchId;
import com.tongbora.common.CustomerId;

public record CreateAccountRequest(
        String accountHolder,
        CustomerId customerId,
        AccountType accountType,
        BranchId branchId,
        Money initialBalance
) {
}

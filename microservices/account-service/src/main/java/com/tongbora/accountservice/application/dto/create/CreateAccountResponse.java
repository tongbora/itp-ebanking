package com.tongbora.accountservice.application.dto.create;

import com.tongbora.common.domain.valueobject.AccountType;
import com.tongbora.common.domain.valueobject.Money;
import com.tongbora.common.AccountId;
import com.tongbora.common.BranchId;
import com.tongbora.common.CustomerId;
import lombok.Builder;

@Builder
public record CreateAccountResponse(

        AccountId accountId,
        String accountNumber,
        String accountHolder,
        CustomerId customerId,
        AccountType accountType,
        BranchId branchId,
        Money initialBalance
) {
}

package com.tongbora.common.domain.event;

import com.tongbora.common.domain.valueobject.AccountType;
import com.tongbora.common.domain.valueobject.Money;
import com.tongbora.common.AccountId;
import com.tongbora.common.BranchId;
import com.tongbora.common.CustomerId;
import lombok.Builder;

import java.time.ZonedDateTime;

@Builder
public record AccountCreatedEvent(
        AccountId accountId,
        String accountNumber,
        String accountHolder,
        CustomerId customerId,
        AccountType accountType,
        BranchId branchId,
        Money initialBalance,
        ZonedDateTime createdAt,
        String createdBy
) {
}

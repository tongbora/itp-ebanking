package com.tongbora.accountservice.domain.event;

import com.tongbora.common.domain.valueobject.AccountStatus;
import com.tongbora.common.AccountId;
import com.tongbora.common.CustomerId;
import lombok.Builder;

import java.time.ZonedDateTime;


@Builder
public record AccountFrozenEvent(
    AccountId accountId,
    CustomerId customerId,
    AccountStatus previousStatus,
    AccountStatus newStatus,
    String reason,
    String requestedBy,
    ZonedDateTime createdAt
) {
}

package com.tongbora.accountservice.domain.command;

import com.tongbora.common.AccountId;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

public record FreezeAccountCommand(
        @TargetAggregateIdentifier
        AccountId accountId,
        String remark,
        String requestedBy
) {
}

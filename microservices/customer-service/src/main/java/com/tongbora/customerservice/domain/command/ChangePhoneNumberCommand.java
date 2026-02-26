package com.tongbora.customerservice.domain.command;

import com.tongbora.common.CustomerId;
import lombok.Builder;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Builder
public record ChangePhoneNumberCommand(
        @TargetAggregateIdentifier
        CustomerId customerId,
        String phoneNumber
) {
}

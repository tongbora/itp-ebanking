package com.tongbora.customerservice.domain.event;

import com.tongbora.common.CustomerId;
import lombok.Builder;

@Builder
public record CustomerPhoneNumberChangedEvent(
        CustomerId customerId,
        String phoneNumber
) {
}

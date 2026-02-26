package com.tongbora.customerservice.domain.event;

import com.tongbora.common.CustomerId;
import com.tongbora.common.CustomerSegmentId;
import com.tongbora.customerservice.domain.valueobject.*;
import lombok.Builder;

import java.time.LocalDate;


@Builder
public record CustomerCreatedEvent(
        CustomerId customerId,
        CustomerName name,
        CustomerEmail email,
        CustomerGender gender,
        LocalDate dob,
        Kyc kyc,
        Address address,
        Contact contact,
        String phoneNumber,
        CustomerSegmentId customerSegmentId
) {
}

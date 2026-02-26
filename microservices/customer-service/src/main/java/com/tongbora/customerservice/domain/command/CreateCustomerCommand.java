package com.tongbora.customerservice.domain.command;

import com.tongbora.common.CustomerId;
import com.tongbora.common.CustomerSegmentId;
import com.tongbora.customerservice.domain.valueobject.*;

import java.time.LocalDate;

public record CreateCustomerCommand(

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

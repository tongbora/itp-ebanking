package com.tongbora.customerservice.application.dto.query;

import com.tongbora.customerservice.domain.valueobject.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public record CustomerResponse(

        UUID customerId,
        @NotNull
        CustomerName name,
        @NotNull
        CustomerEmail email,
        @NotNull
        LocalDate dob,
        @NotNull
        CustomerGender gender,
        @NotNull
        Kyc kyc,
        @NotNull
        Address address,
        @NotNull
        Contact contact,
        @NotBlank
        String phoneNumber,
        @NotNull
        CustomerSegmentResponse customerSegmentId
) {
}

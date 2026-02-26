package com.tongbora.customerservice.application.dto.update;

import com.tongbora.common.CustomerId;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.UUID;

@Builder
public record ChangePhoneNumberResponse(
        @NotNull
        CustomerId customerId,

        @NotBlank
        String phoneNumber,

        @NotBlank
        String message
) {
}

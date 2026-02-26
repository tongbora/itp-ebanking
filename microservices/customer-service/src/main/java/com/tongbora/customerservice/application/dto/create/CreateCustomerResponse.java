package com.tongbora.customerservice.application.dto.create;


import com.tongbora.common.CustomerId;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.UUID;

@Builder
public record CreateCustomerResponse(
        @NotNull
        CustomerId customerId,

        @NotBlank
        String message
) {
}

package com.tongbora.customerservice.application.dto.update;

import jakarta.validation.constraints.NotBlank;

public record ChangePhoneNumberRequest(
        @NotBlank
        String phoneNumber
) {
}

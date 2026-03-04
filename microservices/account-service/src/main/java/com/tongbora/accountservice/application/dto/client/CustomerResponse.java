package com.tongbora.accountservice.application.dto.client;

import java.util.UUID;

public record CustomerResponse(
        UUID customerId
) {
}

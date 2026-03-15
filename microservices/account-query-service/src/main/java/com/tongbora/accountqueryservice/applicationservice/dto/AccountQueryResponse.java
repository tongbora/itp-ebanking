package com.tongbora.accountqueryservice.applicationservice.dto;

import java.util.UUID;

public record AccountQueryResponse(
        UUID accountId,
        String accountNumber
) {
}

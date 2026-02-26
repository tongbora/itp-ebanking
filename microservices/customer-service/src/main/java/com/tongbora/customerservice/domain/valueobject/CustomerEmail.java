package com.tongbora.customerservice.domain.valueobject;

public record CustomerEmail(
        String primaryEmail,
        String secondaryEmail
) {
}

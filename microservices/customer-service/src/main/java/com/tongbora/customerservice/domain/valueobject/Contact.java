package com.tongbora.customerservice.domain.valueobject;

import java.util.UUID;

public record Contact(
        UUID contactId,
        String type,
        String number
) {
}

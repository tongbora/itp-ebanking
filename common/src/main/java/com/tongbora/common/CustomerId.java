package com.tongbora.common;

import java.util.UUID;

public class CustomerId {
    private final UUID value;

    public CustomerId(UUID value) {
        this.value = value;
    }

    public UUID getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}

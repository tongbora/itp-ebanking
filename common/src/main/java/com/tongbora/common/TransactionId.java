package com.tongbora.common;

import java.util.UUID;

public class TransactionId {
    private final UUID value;

    public TransactionId(UUID value) {
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

package com.tongbora.common;

import lombok.Getter;

import java.util.UUID;


public class AccountId
{
    @Getter
    private final UUID value;

    public AccountId(UUID value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}

package com.tongbora.accountservice.data.init;

import com.tongbora.accountservice.data.entity.AccountTypeEntity;
import com.tongbora.accountservice.data.repository.AccountTypeRepository;
import com.tongbora.common.domain.valueobject.AccountType;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AccountTypeInitializer implements CommandLineRunner {

    private final AccountTypeRepository repository;

    @Override
    @Transactional
    public void run(String... args) {

        for (AccountType type : AccountType.values()) {

            boolean exists = repository.existsByAccountType(type);

            if (!exists) {
                AccountTypeEntity entity =
                        new AccountTypeEntity(
                                UUID.randomUUID(),
                                type
                        );

                repository.save(entity);
            }
        }

        System.out.println("Account types initialized!");
    }
}
package com.tongbora.accountservice.dataaccess.init;

import com.tongbora.accountservice.dataaccess.entity.AccountTypeEntity;
import com.tongbora.accountservice.dataaccess.repository.AccountTypeRepository;
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
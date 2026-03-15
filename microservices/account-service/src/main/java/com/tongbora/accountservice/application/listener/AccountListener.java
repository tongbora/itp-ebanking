package com.tongbora.accountservice.application.listener;

import com.tongbora.accountservice.application.mapper.AccountApplicationMapper;
import com.tongbora.accountservice.data.entity.AccountEntity;
import com.tongbora.accountservice.data.entity.AccountTypeEntity;
import com.tongbora.accountservice.data.repository.AccountRepository;
import com.tongbora.accountservice.data.repository.AccountTypeRepository;
import com.tongbora.common.domain.event.AccountCreatedEvent;
import com.tongbora.accountservice.domain.event.AccountFrozenEvent;
import com.tongbora.accountservice.domain.event.MoneyDepositedEvent;
import com.tongbora.accountservice.domain.event.MoneyWithdrawnEvent;
import com.tongbora.common.domain.valueobject.AccountStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Component
@RequiredArgsConstructor
@ProcessingGroup("account-group")
@Slf4j
public class AccountListener {

    private final AccountRepository accountRepository;
    private final AccountTypeRepository accountTypeRepository;
    private final AccountApplicationMapper accountApplicationMapper;


    @EventHandler
    public void on(AccountCreatedEvent accountCreatedEvent){
        log.info("On AccountCreatedEvent: {}", accountCreatedEvent);

        AccountEntity accountEntity = accountApplicationMapper
                .accountCreatedEventToAccountEntity(accountCreatedEvent);

        AccountTypeEntity accountTypeEntity = accountTypeRepository.findByAccountType(accountCreatedEvent.accountType())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account type not found"));
        accountEntity.setAccountType(accountTypeEntity);
        accountEntity.setAccountNumber(UUID.randomUUID().toString());
        accountEntity.setAccountStatus(AccountStatus.ACTIVE);

        accountRepository.save(accountEntity);
    }

    @EventHandler
    public void on(MoneyDepositedEvent moneyDepositedEvent){
        log.info("On MoneyDepositedEvent: {}", moneyDepositedEvent);

        AccountEntity accountEntity = accountRepository.findById(moneyDepositedEvent.accountId().getValue())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"));

        accountEntity.setBalance(moneyDepositedEvent.amount().deposit(moneyDepositedEvent.newBalance()));
        log.info("Amount after deposited: {}", accountEntity.getBalance().getAmount());
        accountRepository.save(accountEntity);
    }

    @EventHandler
    public void on(MoneyWithdrawnEvent moneyWithdrawnEvent){
        log.info("On MoneyWithdrawnEvent: {}", moneyWithdrawnEvent);

        AccountEntity accountEntity = accountRepository.findById(moneyWithdrawnEvent.accountId().getValue())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"));

        accountEntity.setBalance(moneyWithdrawnEvent.amount().withdraw(moneyWithdrawnEvent.newBalance()));
        accountRepository.save(accountEntity);
    }

    @EventHandler
    public void on(AccountFrozenEvent accountFrozenEvent){
        log.info("On AccountFrozenEvent: {}", accountFrozenEvent);

        AccountEntity accountEntity = accountRepository.findById(accountFrozenEvent.accountId().getValue())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"));

        accountEntity.setAccountStatus(accountFrozenEvent.newStatus());
        accountRepository.save(accountEntity);
    }
}

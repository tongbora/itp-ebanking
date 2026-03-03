package com.tongbora.accountservice.application.listener;

import com.tongbora.accountservice.application.mapper.AccountApplicationMapper;
import com.tongbora.accountservice.data.entity.AccountEntity;
import com.tongbora.accountservice.data.entity.AccountTypeEntity;
import com.tongbora.accountservice.data.repository.AccountRepository;
import com.tongbora.accountservice.data.repository.AccountTypeRepository;
import com.tongbora.accountservice.domain.event.AccountCreatedEvent;
import com.tongbora.accountservice.domain.valueobject.AccountStatus;
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
        accountEntity.setAccountTypeId(accountTypeEntity);
        accountEntity.setAccountNumber(UUID.randomUUID().toString());
        accountEntity.setAccountStatus(AccountStatus.ACTIVE);

        accountRepository.save(accountEntity);
    }
}

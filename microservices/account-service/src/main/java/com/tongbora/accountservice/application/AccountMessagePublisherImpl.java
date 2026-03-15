package com.tongbora.accountservice.application;

import com.tongbora.accountservice.application.dto.update.FreezeAccountRequest;
import com.tongbora.accountservice.application.dto.update.MoneyDepositRequest;
import com.tongbora.accountservice.application.dto.update.MoneyWithdrawRequest;
import com.tongbora.accountservice.application.mapper.AccountApplicationMapper;
import com.tongbora.accountservice.application.port.output.message.publisher.AccountMessagePublisher;
import com.tongbora.accountservice.application.port.output.repository.AccountCommandRepository;
import com.tongbora.accountservice.dataaccess.entity.AccountEntity;
import com.tongbora.accountservice.dataaccess.entity.AccountTypeEntity;
import com.tongbora.accountservice.domain.entity.Account;
import com.tongbora.accountservice.domain.event.AccountFrozenEvent;
import com.tongbora.accountservice.domain.event.MoneyDepositedEvent;
import com.tongbora.accountservice.domain.event.MoneyWithdrawnEvent;
import com.tongbora.common.domain.event.AccountCreatedEvent;
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
@ProcessingGroup("account-group")
@RequiredArgsConstructor
@Slf4j
public class AccountMessagePublisherImpl implements AccountMessagePublisher {

    private final AccountCommandRepository accountCommandRepository;
    private final AccountApplicationMapper accountApplicationMapper;

    @Override
    @EventHandler
    public void onAccountCreatedEvent(AccountCreatedEvent accountCreatedEvent) {
        log.info("On AccountCreatedEvent: {}", accountCreatedEvent);

        Account account = accountApplicationMapper
                .accountCreatedEventToAccount(accountCreatedEvent);
        accountCommandRepository.save(account);
    }

    @Override
    @EventHandler
    public void onMoneyDepositedEvent(MoneyDepositedEvent moneyDepositedEvent) {
        log.info("On MoneyDepositedEvent: {}", moneyDepositedEvent);

        MoneyDepositRequest moneyDepositRequest = accountApplicationMapper
                .moneyDepositedEventToMoneyDepositRequest(moneyDepositedEvent);

        accountCommandRepository.deposit(moneyDepositRequest);
    }

    @Override
    @EventHandler
    public void onMoneyWithdrawnEvent(MoneyWithdrawnEvent moneyWithdrawnEvent) {
        log.info("On MoneyWithdrawnEvent: {}", moneyWithdrawnEvent);

        MoneyWithdrawRequest moneyWithdrawRequest = accountApplicationMapper
                .moneyWithdrawnEventToMoneyWithdrawRequest(moneyWithdrawnEvent);

        accountCommandRepository.withdraw(moneyWithdrawRequest);
    }

    @Override
    @EventHandler
    public void onAccountFrozenEvent(AccountFrozenEvent accountFrozenEvent) {
        log.info("On AccountFrozenEvent: {}", accountFrozenEvent);

        FreezeAccountRequest freezeAccountRequest = accountApplicationMapper
                .accountFrozenEventToFreezeAccountRequest(accountFrozenEvent);

        accountCommandRepository.freezeAccount(freezeAccountRequest);
    }
}

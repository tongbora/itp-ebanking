package com.tongbora.accountqueryservice.applicationservice;

import com.tongbora.accountqueryservice.applicationservice.mapper.AccountAppDataMapper;
import com.tongbora.accountqueryservice.applicationservice.ports.input.message.listener.AccountMessageListener;
import com.tongbora.accountqueryservice.applicationservice.ports.output.repository.AccountQueryRepository;
import com.tongbora.accountqueryservice.domain.entity.Account;
import com.tongbora.common.domain.event.AccountCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class AccountMessageListenerImpl implements AccountMessageListener {

    private final AccountQueryRepository accountQueryRepository;
    private final AccountAppDataMapper accountAppDataMapper;

    @Override
    public void onAccountCreatedEvent(AccountCreatedEvent accountCreatedEvent) {

        Account account = accountAppDataMapper.accountCreatedEventToAccount(accountCreatedEvent);
        accountQueryRepository.save(account)
                .doOnSuccess(data ->
                        log.info("Saved account with ID: {}", accountCreatedEvent.accountId()))
                .doOnError(error ->
                        log.error("Error saving account with ID: {}, error: {}", accountCreatedEvent.accountId(), error.getMessage()))
                .subscribe();
    }
}

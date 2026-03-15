package com.tongbora.accountqueryservice.message.listener.axonkafka;


import com.tongbora.accountqueryservice.applicationservice.ports.input.message.listener.AccountMessageListener;
import com.tongbora.common.domain.event.AccountCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

@Component
@ProcessingGroup("account-group")
@Slf4j
@RequiredArgsConstructor
public class AccountAxonKafkaListener {

    private final AccountMessageListener accountMessageListener;

    @EventHandler
    public void on(AccountCreatedEvent accountCreatedEvent){
        log.info("On AccountCreatedEvent: {}", accountCreatedEvent);
        accountMessageListener.onAccountCreatedEvent(accountCreatedEvent);
    }
}

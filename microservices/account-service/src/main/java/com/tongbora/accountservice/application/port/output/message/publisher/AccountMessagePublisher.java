package com.tongbora.accountservice.application.port.output.message.publisher;

import com.tongbora.accountservice.domain.event.AccountFrozenEvent;
import com.tongbora.accountservice.domain.event.MoneyDepositedEvent;
import com.tongbora.accountservice.domain.event.MoneyWithdrawnEvent;
import com.tongbora.common.domain.event.AccountCreatedEvent;

public interface AccountMessagePublisher {

    void onAccountCreatedEvent(AccountCreatedEvent accountCreatedEvent);
    void onMoneyDepositedEvent(MoneyDepositedEvent moneyDepositedEvent);
    void onMoneyWithdrawnEvent(MoneyWithdrawnEvent moneyWithdrawnEvent);
    void onAccountFrozenEvent(AccountFrozenEvent accountFrozenEvent);
}

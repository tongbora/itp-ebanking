package com.tongbora.accountqueryservice.applicationservice.ports.input.message.listener;

import com.tongbora.common.domain.event.AccountCreatedEvent;

public interface AccountMessageListener {

    void onAccountCreatedEvent(AccountCreatedEvent accountCreatedEvent);

}

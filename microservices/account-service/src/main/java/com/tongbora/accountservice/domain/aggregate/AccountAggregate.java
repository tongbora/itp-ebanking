package com.tongbora.accountservice.domain.aggregate;

import com.tongbora.accountservice.domain.command.CreateAccountCommand;
import com.tongbora.accountservice.domain.event.AccountCreatedEvent;
import com.tongbora.accountservice.domain.valueobject.AccountStatus;
import com.tongbora.accountservice.domain.valueobject.AccountType;
import com.tongbora.accountservice.domain.valueobject.Money;
import com.tongbora.common.AccountId;
import com.tongbora.common.BranchId;
import com.tongbora.common.CustomerId;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.time.ZonedDateTime;


@Aggregate
@NoArgsConstructor
@Getter
@EqualsAndHashCode
@Slf4j
public class AccountAggregate {

    @AggregateIdentifier
    AccountId accountId;
    String accountNumber;
    String accountHolder;
    CustomerId customerId;
    AccountType accountTypeCode;
    BranchId branchId;
    Money balance;
    AccountStatus accountStatus;
    ZonedDateTime createdAt;
    String createdBy;
    ZonedDateTime updatedAt;
    String  updatedBy;


    // Domain logic for creating a customer
    @CommandHandler
    public AccountAggregate(CreateAccountCommand createAccountCommand){
        // Validate command data here

        // Public event
        AccountCreatedEvent accountCreatedEvent = AccountCreatedEvent.builder()
                .accountId(createAccountCommand.accountId())
                .accountNumber(createAccountCommand.accountNumber())
                .accountHolder(createAccountCommand.accountHolder())
                .customerId(createAccountCommand.customerId())
                .accountType(createAccountCommand.accountType())
                .branchId(createAccountCommand.branchId())
                .initialBalance(createAccountCommand.initialBalance())
                .createdAt(ZonedDateTime.now())
                .createdBy("system")
                .build();

        AggregateLifecycle.apply(accountCreatedEvent);

    }

    @EventSourcingHandler
    public void on(AccountCreatedEvent accountCreatedEvent) {
        this.accountId = accountCreatedEvent.accountId();
        this.accountNumber = accountCreatedEvent.accountNumber();
        this.accountHolder = accountCreatedEvent.accountHolder();
        this.customerId = accountCreatedEvent.customerId();
        this.accountTypeCode = accountCreatedEvent.accountType();
        this.branchId = accountCreatedEvent.branchId();
        this.balance = accountCreatedEvent.initialBalance();
        this.accountStatus = AccountStatus.ACTIVE;
        this.createdAt = accountCreatedEvent.createdAt();
        this.createdBy = accountCreatedEvent.createdBy();
    }


}

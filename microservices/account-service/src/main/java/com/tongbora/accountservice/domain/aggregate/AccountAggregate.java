package com.tongbora.accountservice.domain.aggregate;

import com.tongbora.accountservice.domain.command.CreateAccountCommand;
import com.tongbora.accountservice.domain.command.DepositMoneyCommand;
import com.tongbora.accountservice.domain.command.FreezeAccountCommand;
import com.tongbora.accountservice.domain.command.WithdrawMoneyCommand;
import com.tongbora.common.domain.event.AccountCreatedEvent;
import com.tongbora.accountservice.domain.event.AccountFrozenEvent;
import com.tongbora.accountservice.domain.event.MoneyDepositedEvent;
import com.tongbora.accountservice.domain.event.MoneyWithdrawnEvent;
import com.tongbora.common.domain.valueobject.AccountStatus;
import com.tongbora.common.domain.valueobject.AccountType;
import com.tongbora.common.domain.valueobject.Money;
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

import java.math.BigDecimal;
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
    AccountType accountType;
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

    // Deposit money
    @CommandHandler
    public AccountId handle(DepositMoneyCommand depositMoneyCommand) {
        if (this.accountStatus != AccountStatus.ACTIVE || this.accountStatus.equals(AccountStatus.FREEZE)) {
            throw new IllegalStateException("Account is not active. Cannot deposit money.");
        }
        if (depositMoneyCommand.amount().getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Deposit amount must be greater than zero.");
        }
        // Build event for apply lifecycle
        MoneyDepositedEvent moneyDepositedEvent = MoneyDepositedEvent.builder()
                .accountId(depositMoneyCommand.accountId())
                .transactionId(depositMoneyCommand.transactionId())
                .amount(this.balance)
                .newBalance(depositMoneyCommand.amount())
                .remark(depositMoneyCommand.remark())
                .createdAt(ZonedDateTime.now())
                .build();

        AggregateLifecycle.apply(moneyDepositedEvent);
        return this.accountId;
    }

    // Withdraw money
    @CommandHandler
    public AccountId handle(WithdrawMoneyCommand withdrawMoneyCommand) {
        if (this.accountStatus != AccountStatus.ACTIVE || this.accountStatus.equals(AccountStatus.FREEZE)) {
            throw new IllegalStateException("Account is not active. Cannot withdraw money.");
        }
        // Build event for apply lifecycle
        MoneyWithdrawnEvent moneyWithdrawnEvent = MoneyWithdrawnEvent.builder()
                .accountId(withdrawMoneyCommand.accountId())
                .transactionId(withdrawMoneyCommand.transactionId())
                .amount(this.balance)
                .newBalance(withdrawMoneyCommand.amount())
                .remark(withdrawMoneyCommand.remark())
                .createdAt(ZonedDateTime.now())
                .build();

        AggregateLifecycle.apply(moneyWithdrawnEvent);
        return this.accountId;
    }

    @CommandHandler
    public AccountId handle(FreezeAccountCommand freezeAccountCommand) {

        // Build event for apply lifecycle
        AccountFrozenEvent frozenEvent = AccountFrozenEvent.builder()
                .accountId(freezeAccountCommand.accountId())
                .reason(freezeAccountCommand.remark())
                .previousStatus(AccountStatus.ACTIVE)
                .newStatus(AccountStatus.FREEZE)
                .requestedBy(freezeAccountCommand.requestedBy())
                .customerId(this.customerId)
                .createdAt(ZonedDateTime.now())
                .build();
        AggregateLifecycle.apply(frozenEvent);
        return this.accountId;
    }

    @EventSourcingHandler
    public void on(AccountCreatedEvent accountCreatedEvent) {
        this.accountId = accountCreatedEvent.accountId();
        this.accountNumber = accountCreatedEvent.accountNumber();
        this.accountHolder = accountCreatedEvent.accountHolder();
        this.customerId = accountCreatedEvent.customerId();
        this.accountType = accountCreatedEvent.accountType();
        this.branchId = accountCreatedEvent.branchId();
        this.balance = accountCreatedEvent.initialBalance();
        this.accountStatus = AccountStatus.ACTIVE;
        this.createdAt = accountCreatedEvent.createdAt();
        this.createdBy = accountCreatedEvent.createdBy();
    }

    @EventSourcingHandler
    public void on(MoneyDepositedEvent moneyDepositedEvent) {
        this.accountId = moneyDepositedEvent.accountId();
        this.balance = this.balance.deposit(moneyDepositedEvent.newBalance());
        this.updatedAt = moneyDepositedEvent.createdAt();
        this.updatedBy = "system";
    }

    @EventSourcingHandler
    public void on(MoneyWithdrawnEvent moneyWithdrawnEvent) {
        this.accountId = moneyWithdrawnEvent.accountId();
        this.balance = this.balance.withdraw(moneyWithdrawnEvent.newBalance());
        this.updatedAt = moneyWithdrawnEvent.createdAt();
        this.updatedBy = "system";
    }

    @EventSourcingHandler
    public void on(AccountFrozenEvent accountFrozenEvent) {
        this.accountId = accountFrozenEvent.accountId();
        this.accountStatus = accountFrozenEvent.newStatus();
        this.updatedAt = accountFrozenEvent.createdAt();
        this.updatedBy = "system";
    }

}

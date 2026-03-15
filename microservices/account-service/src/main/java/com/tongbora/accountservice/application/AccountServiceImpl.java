package com.tongbora.accountservice.application;

import com.tongbora.accountservice.application.dto.create.CreateAccountRequest;
import com.tongbora.accountservice.application.dto.create.CreateAccountResponse;
import com.tongbora.accountservice.application.dto.update.FreezeAccountRequest;
import com.tongbora.accountservice.application.dto.update.MoneyDepositRequest;
import com.tongbora.accountservice.application.dto.update.MoneyResponse;
import com.tongbora.accountservice.application.dto.update.MoneyWithdrawRequest;
import com.tongbora.accountservice.application.mapper.AccountApplicationMapper;
import com.tongbora.accountservice.domain.command.CreateAccountCommand;
import com.tongbora.accountservice.domain.command.DepositMoneyCommand;
import com.tongbora.accountservice.domain.command.FreezeAccountCommand;
import com.tongbora.accountservice.domain.command.WithdrawMoneyCommand;
import com.tongbora.common.AccountId;
import com.tongbora.common.TransactionId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService {

    private final AccountApplicationMapper accountApplicationMapper;
    private final CommandGateway commandGateway;

    @Override
    public CreateAccountResponse createAccount(CreateAccountRequest request) {

        // 1. Transfer data from request to command
        CreateAccountCommand createAccountCommand = accountApplicationMapper
                .createAccountRequestToCreateAccountCommand(new AccountId(UUID.randomUUID()), request);

        AccountId result = commandGateway.sendAndWait(createAccountCommand);
        log.info("Create account result: {}", result);

        return CreateAccountResponse.builder()
                .accountId(result)
                .accountNumber("Generated after account created event")
                .accountHolder(createAccountCommand.accountHolder())
                .customerId(createAccountCommand.customerId())
                .branchId(createAccountCommand.branchId())
                .accountType(createAccountCommand.accountType())
                .initialBalance(createAccountCommand.initialBalance())
                .build();
    }

    @Override
    public MoneyResponse depositMoney(MoneyDepositRequest request) {
        // 1. Transfer data from request to command
        DepositMoneyCommand moneyDepositCommand = accountApplicationMapper
                .moneyDepositRequestToMoneyDepositCommand(new TransactionId(UUID.randomUUID()),request);

        // 2. Send command to command gateway
        AccountId result = commandGateway.sendAndWait(moneyDepositCommand);
        log.info("Deposit money result: {}", result);

        return MoneyResponse.builder()
                .accountId(result)
                .transactionId(moneyDepositCommand.transactionId())
                .amount(request.amount())
                .remark(request.remark())
                .build();
    }

    @Override
    public MoneyResponse withdrawMoney(MoneyWithdrawRequest request) {
        // 1. Transfer data from request to command
        WithdrawMoneyCommand moneyWithdrawCommand = accountApplicationMapper
                .moneyWithdrawRequestToMoneyWithdrawCommand(new TransactionId(UUID.randomUUID()),request);

        AccountId result = commandGateway.sendAndWait(moneyWithdrawCommand);
        log.info("Withdraw money result: {}", result);

        return MoneyResponse.builder()
                .accountId(result)
                .transactionId(moneyWithdrawCommand.transactionId())
                .amount(request.amount())
                .remark(request.remark())
                .build();
    }

    @Override
    public void freezeAccount(FreezeAccountRequest request) {
        // 1. Transfer data from request to command
        FreezeAccountCommand freezeAccountCommand = accountApplicationMapper
                .freezeAccountRequestToFreezeAccountCommand(request);
        AccountId result = commandGateway.sendAndWait(freezeAccountCommand);
        log.info("Freeze account result: {}", result);
    }
}
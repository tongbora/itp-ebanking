package com.tongbora.accountservice.application;

import com.tongbora.accountservice.application.dto.create.CreateAccountRequest;
import com.tongbora.accountservice.application.dto.create.CreateAccountResponse;
import com.tongbora.accountservice.application.mapper.AccountApplicationMapper;
import com.tongbora.accountservice.data.entity.AccountEntity;
import com.tongbora.accountservice.data.repository.AccountRepository;
import com.tongbora.accountservice.domain.command.CreateAccountCommand;
import com.tongbora.common.AccountId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
                .createAccountRequestToCreateAccountCommand(new AccountId(UUID.randomUUID()),request);

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
}

package com.tongbora.accountservice.application.mapper;

import com.tongbora.accountservice.application.dto.create.CreateAccountRequest;
import com.tongbora.accountservice.application.dto.create.CreateAccountResponse;
import com.tongbora.accountservice.data.entity.AccountEntity;
import com.tongbora.accountservice.domain.command.CreateAccountCommand;
import com.tongbora.accountservice.domain.event.AccountCreatedEvent;
import com.tongbora.common.AccountId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AccountApplicationMapper {

    CreateAccountCommand createAccountRequestToCreateAccountCommand(AccountId accountId, CreateAccountRequest createAccountRequest);

    @Mapping(source = "accountId", target = "accountId.value")
    @Mapping(source = "customerId", target = "customerId.value")
    @Mapping(source = "branchId", target = "branchId.value")
    CreateAccountResponse accountEntityToCreateAccountResponse(AccountEntity accountEntity);

    @Mapping(source = "accountId.value", target = "accountId")
    @Mapping(source = "customerId.value", target = "customerId")
    @Mapping(source = "branchId.value", target = "branchId")
    @Mapping(source = "initialBalance", target = "balance")
    AccountEntity accountCreatedEventToAccountEntity(AccountCreatedEvent accountCreatedEvent);
}

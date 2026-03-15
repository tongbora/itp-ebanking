package com.tongbora.accountqueryservice.applicationservice.mapper;

import com.tongbora.accountqueryservice.applicationservice.dto.AccountQueryResponse;
import com.tongbora.accountqueryservice.domain.entity.Account;
import com.tongbora.common.domain.event.AccountCreatedEvent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AccountAppDataMapper {

    AccountQueryResponse accountToAccountQueryResponse(Account account);

    @Mapping(source = "accountId.value", target = "accountId")
    @Mapping(source = "customerId.value", target = "customerId")
    @Mapping(source = "branchId.value", target = "branchId")
    @Mapping(source = "initialBalance", target = "money")
//    @Mapping(source = "accountType.value", target = "accountType")
    Account accountCreatedEventToAccount(AccountCreatedEvent accountCreatedEvent);
}

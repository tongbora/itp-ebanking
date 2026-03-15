package com.tongbora.accountservice.dataaccess.mapper;

import com.tongbora.accountservice.dataaccess.entity.AccountEntity;
import com.tongbora.accountservice.domain.entity.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AccountDataAccessMapper {

    @Mapping(source = "money.amount", target = "balance.amount")
    @Mapping(source = "money.currency", target = "balance.currency")
    AccountEntity accountToAccountEntity(Account account);
    Account accountEntityToAccount(AccountEntity accountEntity);
}

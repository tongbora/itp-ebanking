package com.tongbora.accountqueryservice.dataaccess.mapper;

import com.tongbora.accountqueryservice.dataaccess.entity.AccountEntity;
import com.tongbora.accountqueryservice.domain.entity.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AccountDataAccessMapper {


    @Mapping(source = "money.amount", target = "balance")
    @Mapping(source = "money.currency", target = "currency")
    AccountEntity accountToAccountEntity(Account account);
    Account accountEntityToAccount(AccountEntity accountEntity);
}

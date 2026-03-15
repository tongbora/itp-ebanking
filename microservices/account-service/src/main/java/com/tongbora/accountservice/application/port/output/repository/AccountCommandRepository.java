package com.tongbora.accountservice.application.port.output.repository;

import com.tongbora.accountservice.application.dto.update.FreezeAccountRequest;
import com.tongbora.accountservice.application.dto.update.MoneyDepositRequest;
import com.tongbora.accountservice.application.dto.update.MoneyWithdrawRequest;
import com.tongbora.accountservice.domain.entity.Account;
import org.springframework.stereotype.Repository;


@Repository
public interface AccountCommandRepository {
    Account save(Account account);
    Account deposit(MoneyDepositRequest  request);
    Account withdraw(MoneyWithdrawRequest request);
    void freezeAccount(FreezeAccountRequest request);
}

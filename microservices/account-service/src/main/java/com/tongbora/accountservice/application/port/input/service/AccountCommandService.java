package com.tongbora.accountservice.application.port.input.service;

import com.tongbora.accountservice.application.dto.create.CreateAccountRequest;
import com.tongbora.accountservice.application.dto.create.CreateAccountResponse;
import com.tongbora.accountservice.application.dto.update.FreezeAccountRequest;
import com.tongbora.accountservice.application.dto.update.MoneyDepositRequest;
import com.tongbora.accountservice.application.dto.update.MoneyResponse;
import com.tongbora.accountservice.application.dto.update.MoneyWithdrawRequest;

public interface AccountCommandService {

    CreateAccountResponse createAccount(CreateAccountRequest request);
    MoneyResponse depositMoney(MoneyDepositRequest request);
    MoneyResponse withdrawMoney(MoneyWithdrawRequest request);
    void freezeAccount(FreezeAccountRequest request);
}

package com.tongbora.accountservice.rest.controller;

import com.tongbora.accountservice.application.dto.create.CreateAccountRequest;
import com.tongbora.accountservice.application.dto.create.CreateAccountResponse;
import com.tongbora.accountservice.application.dto.update.FreezeAccountRequest;
import com.tongbora.accountservice.application.dto.update.MoneyDepositRequest;
import com.tongbora.accountservice.application.dto.update.MoneyResponse;
import com.tongbora.accountservice.application.dto.update.MoneyWithdrawRequest;
import com.tongbora.accountservice.application.port.input.service.AccountCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountCommandService accountCommandService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CreateAccountResponse createAccount(@RequestBody CreateAccountRequest createAccountRequest) {
        return accountCommandService.createAccount(createAccountRequest);
    }

    @PatchMapping("/deposit")
    @ResponseStatus(HttpStatus.OK)
    public MoneyResponse depositMoney(@RequestBody MoneyDepositRequest moneyDepositRequest) {
        return accountCommandService.depositMoney(moneyDepositRequest);
    }

    @PatchMapping("/withdraw")
    @ResponseStatus(HttpStatus.OK)
    public MoneyResponse depositMoney(@RequestBody MoneyWithdrawRequest moneyWithdrawRequest) {
        return accountCommandService.withdrawMoney(moneyWithdrawRequest);
    }

    @PatchMapping("/freeze")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void freezeAccount(@RequestBody FreezeAccountRequest freezeAccountRequest) {
        accountCommandService.freezeAccount(freezeAccountRequest);
    }
}

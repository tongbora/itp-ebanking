package com.tongbora.accountservice.application;

import com.tongbora.accountservice.application.dto.create.CreateAccountRequest;
import com.tongbora.accountservice.application.dto.create.CreateAccountResponse;

public interface AccountService {

    CreateAccountResponse createAccount(CreateAccountRequest request);
}

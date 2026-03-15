package com.tongbora.accountqueryservice.rest;

import com.tongbora.accountqueryservice.applicationservice.dto.AccountQueryResponse;
import com.tongbora.accountqueryservice.applicationservice.ports.input.service.AccountQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountQueryController {

    private final AccountQueryService accountQueryService;

    @GetMapping("/{accountId}")
    Mono<AccountQueryResponse> getAccountById(@PathVariable UUID accountId) {
        return accountQueryService.getAccountById(accountId);
    }
}

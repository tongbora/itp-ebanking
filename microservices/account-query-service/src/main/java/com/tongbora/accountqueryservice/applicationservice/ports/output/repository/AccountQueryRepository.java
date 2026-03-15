package com.tongbora.accountqueryservice.applicationservice.ports.output.repository;

import com.tongbora.accountqueryservice.domain.entity.Account;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface AccountQueryRepository {

    // Save account
    Mono<Account> save(Account account);

    Mono<Account> findById(UUID accountId);
}
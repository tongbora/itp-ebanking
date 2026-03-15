package com.tongbora.accountqueryservice.applicationservice.ports.input.service;


import com.tongbora.accountqueryservice.applicationservice.dto.AccountQueryResponse;
import reactor.core.publisher.Mono;

import java.util.UUID;

// Input port for application service
public interface AccountQueryService {

    Mono<AccountQueryResponse> getAccountById(UUID accountId);
}

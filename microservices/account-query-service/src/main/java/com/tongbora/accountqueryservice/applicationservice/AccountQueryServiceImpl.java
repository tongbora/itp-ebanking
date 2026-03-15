package com.tongbora.accountqueryservice.applicationservice;

import com.tongbora.accountqueryservice.applicationservice.dto.AccountQueryResponse;
import com.tongbora.accountqueryservice.applicationservice.mapper.AccountAppDataMapper;
import com.tongbora.accountqueryservice.applicationservice.ports.input.service.AccountQueryService;
import com.tongbora.accountqueryservice.applicationservice.ports.output.repository.AccountQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountQueryServiceImpl implements AccountQueryService {

    private final AccountQueryRepository accountQueryRepository;
    private final AccountAppDataMapper accountAppDataMapper;

    @Override
    public Mono<AccountQueryResponse> getAccountById(UUID accountId) {
        return accountQueryRepository
                .findById(accountId)
                .map(accountAppDataMapper::accountToAccountQueryResponse);
    }
}

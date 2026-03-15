package com.tongbora.accountqueryservice.dataaccess.adapter;

import com.tongbora.accountqueryservice.applicationservice.ports.output.repository.AccountQueryRepository;
import com.tongbora.accountqueryservice.dataaccess.entity.AccountEntity;
import com.tongbora.accountqueryservice.dataaccess.mapper.AccountDataAccessMapper;
import com.tongbora.accountqueryservice.dataaccess.repository.AccountQueryReactiveRepository;
import com.tongbora.accountqueryservice.domain.entity.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class AccountQueryRepositoryImpl implements AccountQueryRepository {

    private final AccountQueryReactiveRepository accountQueryReactiveRepository;
    private final AccountDataAccessMapper accountDataAccessMapper;

    @Override
    public Mono<Account> save(Account account) {

        AccountEntity accountEntity = accountDataAccessMapper.accountToAccountEntity(account);

        return accountQueryReactiveRepository
                .save(accountEntity)
                .map(accountDataAccessMapper::accountEntityToAccount);
    }

    @Override
    public Mono<Account> findById(UUID accountId) {

        return accountQueryReactiveRepository
                .findById(accountId)
                .map(accountDataAccessMapper::accountEntityToAccount);
    }
}

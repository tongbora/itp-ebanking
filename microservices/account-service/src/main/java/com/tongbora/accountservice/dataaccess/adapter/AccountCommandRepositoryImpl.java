package com.tongbora.accountservice.dataaccess.adapter;

import com.tongbora.accountservice.application.dto.update.FreezeAccountRequest;
import com.tongbora.accountservice.application.dto.update.MoneyDepositRequest;
import com.tongbora.accountservice.application.dto.update.MoneyWithdrawRequest;
import com.tongbora.accountservice.application.port.output.repository.AccountCommandRepository;
import com.tongbora.accountservice.dataaccess.entity.AccountEntity;
import com.tongbora.accountservice.dataaccess.entity.AccountTypeEntity;
import com.tongbora.accountservice.dataaccess.mapper.AccountDataAccessMapper;
import com.tongbora.accountservice.dataaccess.repository.AccountCommandJpaRepository;
import com.tongbora.accountservice.dataaccess.repository.AccountTypeRepository;
import com.tongbora.accountservice.domain.entity.Account;
import com.tongbora.common.domain.valueobject.AccountStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
@Slf4j
public class AccountCommandRepositoryImpl implements AccountCommandRepository {

    private final AccountCommandJpaRepository accountCommandJpaRepository;
    private final AccountTypeRepository accountTypeRepository;
    private final AccountDataAccessMapper accountDataAccessMapper;

    @Override
    public Account save(Account account) {

        log.info("Saving account in adapter: {}", account);

//        AccountTypeEntity accountTypeEntity = accountTypeRepository.findById(account.getAccountTypeId())
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account type not found"));
        AccountEntity accountEntity = accountDataAccessMapper.accountToAccountEntity(account);

//        accountEntity.setAccountType(accountTypeEntity);
        accountEntity.setAccountNumber(UUID.randomUUID().toString());
        accountEntity.setAccountStatus(AccountStatus.ACTIVE);
        accountCommandJpaRepository.save(accountEntity);
        log.info("Account saved in adapter: {}", accountEntity);
        return accountDataAccessMapper.accountEntityToAccount(accountEntity);
    }

    @Override
    public Account deposit(MoneyDepositRequest moneyDepositRequest) {

        log.info("Deposit money in adapter: {}", moneyDepositRequest);

        AccountEntity accountEntity = accountCommandJpaRepository.findById(moneyDepositRequest.accountId().getValue())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"));

        accountEntity.setBalance(accountEntity.getBalance().deposit(moneyDepositRequest.amount()));
        accountCommandJpaRepository.save(accountEntity);
        log.info("Amount after deposited in adapter: {}", accountEntity.getBalance().getAmount());
        return accountDataAccessMapper.accountEntityToAccount(accountEntity);
    }

    @Override
    public Account withdraw(MoneyWithdrawRequest moneyWithdrawRequest) {
        log.info("Withdraw money in adapter: {}", moneyWithdrawRequest);
        AccountEntity accountEntity = accountCommandJpaRepository.findById(moneyWithdrawRequest.accountId().getValue())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"));

        accountEntity.setBalance(accountEntity.getBalance().withdraw(moneyWithdrawRequest.amount()));
        accountCommandJpaRepository.save(accountEntity);
        log.info("Amount after withdraw in adapter: {}", accountEntity.getBalance().getAmount());
        return accountDataAccessMapper.accountEntityToAccount(accountEntity);
    }

    @Override
    public void freezeAccount(FreezeAccountRequest freezeAccountRequest) {
        AccountEntity accountEntity = accountCommandJpaRepository.findById(freezeAccountRequest.accountId().getValue())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"));

        accountEntity.setAccountStatus(AccountStatus.FREEZE);
        accountCommandJpaRepository.save(accountEntity);
        log.info("Account frozen in adapter: {}", accountEntity.getAccountId());
    }
}

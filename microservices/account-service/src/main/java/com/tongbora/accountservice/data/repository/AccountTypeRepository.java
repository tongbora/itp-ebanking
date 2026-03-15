package com.tongbora.accountservice.data.repository;

import com.tongbora.accountservice.data.entity.AccountTypeEntity;
import com.tongbora.common.domain.valueobject.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AccountTypeRepository
        extends JpaRepository<AccountTypeEntity, UUID> {

    boolean existsByAccountType(AccountType accountType);
    Optional<AccountTypeEntity> findByAccountType(AccountType accountType);

}

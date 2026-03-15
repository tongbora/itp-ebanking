package com.tongbora.accountservice.dataaccess.repository;

import com.tongbora.accountservice.dataaccess.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AccountCommandJpaRepository extends JpaRepository<AccountEntity, UUID> {
}

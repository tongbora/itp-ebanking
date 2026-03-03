package com.tongbora.accountservice.data.repository;

import com.tongbora.accountservice.data.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AccountRepository extends JpaRepository<AccountEntity, UUID> {
}

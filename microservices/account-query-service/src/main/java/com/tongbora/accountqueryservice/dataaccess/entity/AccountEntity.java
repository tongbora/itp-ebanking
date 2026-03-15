package com.tongbora.accountqueryservice.dataaccess.entity;

import com.tongbora.common.domain.valueobject.AccountStatus;
import com.tongbora.common.domain.valueobject.AccountType;
import com.tongbora.common.domain.valueobject.Currency;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "accounts")
public class AccountEntity {

    @Id
    private UUID accountId;

    private String accountNumber;
    private String accountHolder;


    private UUID customerId;

    private BigDecimal balance;

    private Currency currency;

    private UUID accountTypeId;

    private UUID branchId;

    private AccountStatus status;

    private ZonedDateTime createdAt;
    private String createdBy;
    private ZonedDateTime updatedAt;
    private String updatedBy;

    @Version
    private Integer version;
}

package com.tongbora.accountservice.dataaccess.entity;


import com.tongbora.common.domain.valueobject.AccountStatus;
import com.tongbora.common.domain.valueobject.Money;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "accounts")
public class AccountEntity {

    @Id
    private UUID accountId;

    private String accountNumber;
    private String accountHolder;


    private UUID customerId;

    @ManyToOne
    @JoinColumn(name = "account_type_id")
    private AccountTypeEntity accountType;

    private UUID branchId;

    @Embedded
    private Money balance;

    @Enumerated(EnumType.STRING)
    private AccountStatus accountStatus;

    private ZonedDateTime createdAt;
    private String createdBy;
    private ZonedDateTime updatedAt;
    private String updatedBy;
}

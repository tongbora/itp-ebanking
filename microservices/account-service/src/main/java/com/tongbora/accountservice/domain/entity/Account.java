package com.tongbora.accountservice.domain.entity;

import com.tongbora.common.domain.valueobject.AccountStatus;
import com.tongbora.common.domain.valueobject.Money;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.time.ZonedDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class Account {

    @Id
    private UUID accountId;

    private String accountNumber;

    private String accountHolder;

    private UUID customerId;

    private Money money;

    private UUID accountTypeId;

    private UUID branchId;

    private AccountStatus status;

    private ZonedDateTime createdAt;

    private String createdBy;

    private ZonedDateTime updatedAt;

    private String updatedBy;

}

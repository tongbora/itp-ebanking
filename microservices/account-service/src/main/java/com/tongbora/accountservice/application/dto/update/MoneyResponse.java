package com.tongbora.accountservice.application.dto.update;

import com.tongbora.common.domain.valueobject.Money;
import com.tongbora.common.AccountId;
import com.tongbora.common.TransactionId;
import lombok.Builder;

@Builder
public record MoneyResponse(
        AccountId accountId,
        TransactionId transactionId,
        Money amount,
        String remark
){

}
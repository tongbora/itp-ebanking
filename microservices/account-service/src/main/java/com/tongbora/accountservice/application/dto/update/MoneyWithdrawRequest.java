package com.tongbora.accountservice.application.dto.update;

import com.tongbora.common.domain.valueobject.Money;
import com.tongbora.common.AccountId;

public record MoneyWithdrawRequest(
        AccountId accountId,
        Money amount,
        String remark
) {
}

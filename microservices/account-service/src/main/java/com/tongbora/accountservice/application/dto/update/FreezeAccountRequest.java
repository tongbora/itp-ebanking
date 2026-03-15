package com.tongbora.accountservice.application.dto.update;
import com.tongbora.common.AccountId;

public record FreezeAccountRequest(
        AccountId accountId,
        String remark,
        String requestedBy
) {
}

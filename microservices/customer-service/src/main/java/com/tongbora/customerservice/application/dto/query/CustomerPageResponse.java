package com.tongbora.customerservice.application.dto.query;

import java.util.List;

public record CustomerPageResponse(
        List<CustomerResponse> content,
        int pageNumber,
        int pageSize,
        long totalElements,
        int totalPages
) {
}

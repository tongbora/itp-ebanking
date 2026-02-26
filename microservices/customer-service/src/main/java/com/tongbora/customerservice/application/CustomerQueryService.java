package com.tongbora.customerservice.application;

import com.tongbora.customerservice.application.dto.query.CustomerPageResponse;
import com.tongbora.customerservice.application.dto.query.CustomerResponse;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface CustomerQueryService {

    Page<CustomerResponse> getAllCustomers(int pageNumber, int pageSize);
    CustomerPageResponse getAllCustomersWithCustomPage(int pageNumber, int pageSize);

    List<?> getCustomerHistory(UUID customerId);
}

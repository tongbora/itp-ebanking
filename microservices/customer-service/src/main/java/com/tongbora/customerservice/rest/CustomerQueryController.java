package com.tongbora.customerservice.rest;

import com.tongbora.customerservice.application.CustomerQueryService;
import com.tongbora.customerservice.application.dto.query.CustomerPageResponse;
import com.tongbora.customerservice.application.dto.query.CustomerResponse;
import com.tongbora.customerservice.application.projection.GetCustomerQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
@Slf4j
public class CustomerQueryController {

    private final CustomerQueryService customerQueryService;

    @GetMapping
    public Page<CustomerResponse> getAllCustomers(
            @RequestParam(defaultValue = "0", required = false) int pageNumber,
            @RequestParam(defaultValue = "5", required = false) int pageSize
    ) {
       return customerQueryService.getAllCustomers(pageNumber, pageSize);
    }

    @GetMapping("/custom-page")
    public CustomerPageResponse getAllCustomersWithCustomPage(
            @RequestParam(defaultValue = "0", required = false) int pageNumber,
            @RequestParam(defaultValue = "5", required = false) int pageSize
    ) {
        return customerQueryService.getAllCustomersWithCustomPage(pageNumber, pageSize);
    }

    @GetMapping("/{customerId}/history")
    public List<?> getCustomerHistory(@PathVariable UUID customerId){
        return customerQueryService.getCustomerHistory(customerId);
    }
}

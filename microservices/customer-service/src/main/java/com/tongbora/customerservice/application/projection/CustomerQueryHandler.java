package com.tongbora.customerservice.application.projection;

import com.tongbora.customerservice.application.dto.query.CustomerPageResponse;
import com.tongbora.customerservice.application.dto.query.CustomerResponse;
import com.tongbora.customerservice.application.mapper.CustomerApplicationMapper;
import com.tongbora.customerservice.data.entity.CustomerEntity;
import com.tongbora.customerservice.data.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomerQueryHandler {

    private final CustomerRepository customerRepository;
    private final CustomerApplicationMapper customerApplicationMapper;

    @QueryHandler
    public Page<CustomerResponse> handle(GetCustomerQuery getCustomerQuery){

        Pageable pageable = PageRequest.of(
                getCustomerQuery.getPageNumber(),
                getCustomerQuery.getPageSize(),
                Sort.by(Sort.Direction.DESC, "dob")
        );

        Page<CustomerEntity> customers = customerRepository.findAll(pageable);

        return customers
                .map(customerApplicationMapper::customerEntityToCustomerResponse);
    }
}

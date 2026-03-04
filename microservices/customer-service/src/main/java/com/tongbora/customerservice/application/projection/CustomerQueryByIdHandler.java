package com.tongbora.customerservice.application.projection;

import com.tongbora.customerservice.application.dto.query.CustomerResponse;
import com.tongbora.customerservice.application.mapper.CustomerApplicationMapper;
import com.tongbora.customerservice.data.entity.CustomerEntity;
import com.tongbora.customerservice.data.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomerQueryByIdHandler {

    private final CustomerRepository customerRepository;
    private final CustomerApplicationMapper customerApplicationMapper;

    @QueryHandler
    public CustomerResponse handle(GetCustomerQueryById getCustomerQueryById){

       CustomerEntity customerEntity = customerRepository.findById(getCustomerQueryById.customerId)
               .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));

        return customerApplicationMapper.customerEntityToCustomerResponse(customerEntity);
    }

}

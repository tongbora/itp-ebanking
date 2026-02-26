package com.tongbora.customerservice.application.projection;

import com.tongbora.customerservice.application.dto.query.CustomerPageResponse;
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
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomerCustomPageHandler {
    private final CustomerRepository customerRepository;
    private final CustomerApplicationMapper customerApplicationMapper;

    @QueryHandler
    public CustomerPageResponse handle(GetCustomerCustomPageQuery getCustomerQuery){

        Pageable pageable = PageRequest.of(getCustomerQuery.getPageNumber(), getCustomerQuery.getPageSize());
        Page<CustomerEntity> page = customerRepository.findAll(pageable);

        List<CustomerResponse> content = page.getContent().stream()
                .map(customerApplicationMapper::customerEntityToCustomerResponse)
                .toList();

        return new CustomerPageResponse(
                content,
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages()
        );
    }

}

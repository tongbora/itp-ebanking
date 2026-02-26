package com.tongbora.customerservice.application.mapper;

import com.tongbora.common.CustomerId;
import com.tongbora.customerservice.application.dto.create.CreateCustomerRequest;
import com.tongbora.customerservice.application.dto.query.CustomerResponse;
import com.tongbora.customerservice.data.entity.CustomerEntity;
import com.tongbora.customerservice.domain.command.CreateCustomerCommand;
import com.tongbora.customerservice.domain.event.CustomerCreatedEvent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface CustomerApplicationMapper {

    CustomerResponse customerEntityToCustomerResponse(CustomerEntity customerEntity);

    CreateCustomerCommand createCustomerRequestToCreateCustomerCommand(CustomerId customerId, CreateCustomerRequest createCustomerRequest);

    @Mapping(source = "customerId.value", target = "customerId")
    CustomerEntity customerCreatedEventToCustomerEntity(CustomerCreatedEvent customerCreatedEvent);

}

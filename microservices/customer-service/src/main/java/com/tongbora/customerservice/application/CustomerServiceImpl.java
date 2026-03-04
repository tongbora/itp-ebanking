package com.tongbora.customerservice.application;

import com.tongbora.common.CustomerId;
import com.tongbora.customerservice.application.dto.create.CreateCustomerRequest;
import com.tongbora.customerservice.application.dto.create.CreateCustomerResponse;
import com.tongbora.customerservice.application.dto.query.CustomerResponse;
import com.tongbora.customerservice.application.dto.update.ChangePhoneNumberRequest;
import com.tongbora.customerservice.application.dto.update.ChangePhoneNumberResponse;
import com.tongbora.customerservice.application.mapper.CustomerApplicationMapper;
import com.tongbora.customerservice.domain.command.ChangePhoneNumberCommand;
import com.tongbora.customerservice.domain.command.CreateCustomerCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    private final CustomerApplicationMapper customerApplicationMapper;
    private final CommandGateway commandGateway;


    @Override
    public CreateCustomerResponse createCustomer(CreateCustomerRequest createCustomerRequest) {
        // 1. Transfer data from request to command
        CreateCustomerCommand createCustomerCommand = customerApplicationMapper
                .createCustomerRequestToCreateCustomerCommand(new CustomerId(UUID.randomUUID()), createCustomerRequest);
        log.info("Create customer command: {}", createCustomerCommand);

        // 2. Invoke and handle Axon command gateway
        // Command gateway will respond the aggregate identifier
        CustomerId result = commandGateway.sendAndWait(createCustomerCommand);
        log.info("Create customer result: {}", result);
        return CreateCustomerResponse.builder()
                .customerId(result)
                .message("Customer created successfully")
                .build();
    }

    @Override
    public ChangePhoneNumberResponse changePhoneNumber(UUID customerId, ChangePhoneNumberRequest changePhoneNumberRequest) {
        // 1. Change phone number request to command
        ChangePhoneNumberCommand changePhoneNumberCommand = ChangePhoneNumberCommand.builder()
                .customerId(new CustomerId(customerId))
                .phoneNumber(changePhoneNumberRequest.phoneNumber())
                .build();
        log.info("Change phone number command: {}", changePhoneNumberCommand);

        // Command gateway will respond the aggregate identifier
        CustomerId result = commandGateway.sendAndWait(changePhoneNumberCommand);
        log.info("Change phone number result: {}", result);

        return ChangePhoneNumberResponse.builder()
                .customerId(result)
                .phoneNumber(changePhoneNumberCommand.phoneNumber())
                .message("Phone number changed successfully")
                .build();
    }
}

package com.tongbora.customerservice.application;

import com.tongbora.customerservice.application.dto.create.CreateCustomerRequest;
import com.tongbora.customerservice.application.dto.create.CreateCustomerResponse;
import com.tongbora.customerservice.application.dto.query.CustomerResponse;
import com.tongbora.customerservice.application.dto.update.ChangePhoneNumberRequest;
import com.tongbora.customerservice.application.dto.update.ChangePhoneNumberResponse;

import java.util.UUID;


public interface CustomerService {
    CreateCustomerResponse createCustomer(CreateCustomerRequest createCustomerRequest);
    ChangePhoneNumberResponse changePhoneNumber(UUID customerId, ChangePhoneNumberRequest changePhoneNumberRequest);
}

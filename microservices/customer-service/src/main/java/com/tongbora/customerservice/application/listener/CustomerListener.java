package com.tongbora.customerservice.application.listener;


import com.tongbora.customerservice.application.mapper.CustomerApplicationMapper;
import com.tongbora.customerservice.data.entity.CustomerEntity;
import com.tongbora.customerservice.data.entity.CustomerSegmentEntity;
import com.tongbora.customerservice.data.repository.CustomerRepository;
import com.tongbora.customerservice.data.repository.CustomerSegmentRepository;
import com.tongbora.customerservice.domain.event.CustomerCreatedEvent;
import com.tongbora.customerservice.domain.event.CustomerPhoneNumberChangedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
@RequiredArgsConstructor
@ProcessingGroup("customer-group")
@Slf4j
public class CustomerListener {

    private final CustomerRepository customerRepository;
    private final CustomerSegmentRepository customerSegmentRepository;
    private final CustomerApplicationMapper customerApplicationMapper;

    @EventHandler
    public void on(CustomerCreatedEvent customerCreatedEvent){
        log.info("On CustomerCreatedEvent: {}", customerCreatedEvent);

        CustomerEntity customerEntity = customerApplicationMapper
                .customerCreatedEventToCustomerEntity(customerCreatedEvent);

        customerEntity.getAddress().setCustomer(customerEntity);
        customerEntity.getContact().setCustomer(customerEntity);
        customerEntity.getKyc().setCustomer(customerEntity);

        CustomerSegmentEntity customerSegment = customerSegmentRepository
                .findById(customerCreatedEvent.customerSegmentId().value())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer segment not found"));

        customerEntity.setCustomerSegment(customerSegment);
        customerRepository.save(customerEntity);
    }

    @EventHandler
    public void on(CustomerPhoneNumberChangedEvent customerPhoneNumberChangedEvent){
        log.info("On ChangePhoneNumberEvent: {}", customerPhoneNumberChangedEvent);

        CustomerEntity customerEntity = customerRepository.findById(customerPhoneNumberChangedEvent.customerId().getValue())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));

        customerEntity.setPhoneNumber(customerPhoneNumberChangedEvent.phoneNumber());
        customerRepository.save(customerEntity);
    }

}

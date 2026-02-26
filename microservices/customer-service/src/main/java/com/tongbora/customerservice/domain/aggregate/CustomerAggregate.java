package com.tongbora.customerservice.domain.aggregate;


import com.tongbora.common.CustomerId;
import com.tongbora.common.CustomerSegmentId;
import com.tongbora.customerservice.domain.command.ChangePhoneNumberCommand;
import com.tongbora.customerservice.domain.command.CreateCustomerCommand;
import com.tongbora.customerservice.domain.event.CustomerCreatedEvent;
import com.tongbora.customerservice.domain.event.CustomerPhoneNumberChangedEvent;
import com.tongbora.customerservice.domain.valueobject.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.time.LocalDate;
import java.util.List;

@Aggregate
@NoArgsConstructor // required by Axon Framework for event sourcing
@Getter
@EqualsAndHashCode
@Slf4j
public class CustomerAggregate {

    @AggregateIdentifier
    private CustomerId customerId;
    private CustomerName name;
    private CustomerEmail email;
    private CustomerGender gender;
    private LocalDate dob;
    private Kyc kyc;
    private Address address;
    private Contact contact;
    private String phoneNumber;
    private CustomerSegmentId customerSegmentId;
    List<String> failureMessages;

    // Domain logic for creating a customer
    @CommandHandler
    public CustomerAggregate(CreateCustomerCommand createCustomerCommand){
        // Validate command data (omitted for brevity)

        // Public event
        CustomerCreatedEvent customerCreatedEvent =
                CustomerCreatedEvent.builder()
                        .customerId(createCustomerCommand.customerId())
                        .name(createCustomerCommand.name())
                        .email(createCustomerCommand.email())
                        .gender(createCustomerCommand.gender())
                        .dob(createCustomerCommand.dob())
                        .kyc(createCustomerCommand.kyc())
                        .address(createCustomerCommand.address())
                        .contact(createCustomerCommand.contact())
                        .phoneNumber(createCustomerCommand.phoneNumber())
                        .customerSegmentId(createCustomerCommand.customerSegmentId())
                        .build();

        AggregateLifecycle.apply(customerCreatedEvent);
    }

    @CommandHandler
    public CustomerId handle(ChangePhoneNumberCommand changePhoneNumberCommand){
        log.info("Handling ChangePhoneNumberCommand: {}", changePhoneNumberCommand);

        CustomerPhoneNumberChangedEvent customerPhoneNumberChangedEvent = CustomerPhoneNumberChangedEvent.builder()
                .customerId(changePhoneNumberCommand.customerId())
                .phoneNumber(changePhoneNumberCommand.phoneNumber())
                .build();

        AggregateLifecycle.apply(customerPhoneNumberChangedEvent);

        return this.customerId;
    }


    @EventSourcingHandler
    public void on(CustomerCreatedEvent customerCreatedEvent){
        this.customerId = customerCreatedEvent.customerId();
        this.name = customerCreatedEvent.name();
        this.email = customerCreatedEvent.email();
        this.gender = customerCreatedEvent.gender();
        this.dob = customerCreatedEvent.dob();
        this.kyc = customerCreatedEvent.kyc();
        this.address = customerCreatedEvent.address();
        this.contact = customerCreatedEvent.contact();
        this.phoneNumber = customerCreatedEvent.phoneNumber();
        this.customerSegmentId = customerCreatedEvent.customerSegmentId();
    }

    @EventSourcingHandler
    public void on(CustomerPhoneNumberChangedEvent customerPhoneNumberChangedEvent){
        this.customerId = customerPhoneNumberChangedEvent.customerId();
        this.phoneNumber = customerPhoneNumberChangedEvent.phoneNumber();
    }
}

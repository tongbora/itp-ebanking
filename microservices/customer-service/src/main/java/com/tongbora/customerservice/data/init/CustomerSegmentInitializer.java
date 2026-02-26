package com.tongbora.customerservice.data.init;

import com.tongbora.customerservice.data.entity.CustomerSegmentEntity;
import com.tongbora.customerservice.data.repository.CustomerSegmentRepository;
import com.tongbora.customerservice.domain.valueobject.CustomerSegmentType;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CustomerSegmentInitializer implements CommandLineRunner {

    private final CustomerSegmentRepository repository;

    @Override
    public void run(String... args) {

        if (repository.count() == 0) {

            for (CustomerSegmentType type : CustomerSegmentType.values()) {
                CustomerSegmentEntity segment = new CustomerSegmentEntity();
                segment.setCustomerSegmentId(UUID.randomUUID());
                segment.setCustomerSegmentType(type);

                repository.save(segment);
            }

            System.out.println("Customer segments initialized!");
        }
    }
}
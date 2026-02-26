package com.tongbora.customerservice.application;

import com.tongbora.customerservice.application.dto.query.CustomerPageResponse;
import com.tongbora.customerservice.application.dto.query.CustomerResponse;
import com.tongbora.customerservice.application.projection.GetCustomerCustomPageQuery;
import com.tongbora.customerservice.application.projection.GetCustomerQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerQueryServiceImpl implements CustomerQueryService{

    private final QueryGateway queryGateway;
    private final EventStore eventStore;

    @Override
    public Page<CustomerResponse> getAllCustomers(int pageNumber, int pageSize) {
        GetCustomerQuery getCustomerQuery = new GetCustomerQuery(pageNumber,pageSize);

        List<CustomerResponse> customerResponse =  queryGateway.query(getCustomerQuery,
                        ResponseTypes.multipleInstancesOf(CustomerResponse.class))
                .join();

        return new PageImpl<>(
                customerResponse,
                PageRequest.of(pageNumber, pageSize),
                customerResponse.size()
        );
    }

    @Override
    public CustomerPageResponse getAllCustomersWithCustomPage(int pageNumber, int pageSize) {

        GetCustomerCustomPageQuery getCustomerQuery = new GetCustomerCustomPageQuery(pageNumber,pageSize);

        return queryGateway.query(getCustomerQuery,
                        ResponseTypes.instanceOf(CustomerPageResponse.class))
                .join();
    }

    @Override
    public List<?> getCustomerHistory(UUID customerId) {

        return  eventStore.readEvents(customerId.toString()).asStream()
                .map(event -> event.getPayload())
                .toList();
    }
}

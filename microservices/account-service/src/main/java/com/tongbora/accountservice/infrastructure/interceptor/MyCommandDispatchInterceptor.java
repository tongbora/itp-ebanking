package com.tongbora.accountservice.infrastructure.interceptor;

import com.tongbora.accountservice.domain.command.CreateAccountCommand;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.messaging.MessageDispatchInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;
import java.util.function.BiFunction;

@RequiredArgsConstructor
@Component
public class MyCommandDispatchInterceptor
        implements MessageDispatchInterceptor<CommandMessage<?>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyCommandDispatchInterceptor.class);
    private final WebClient.Builder customerWebClientBuilder;

    @Override
    public BiFunction<Integer, CommandMessage<?>, CommandMessage<?>> handle(
            List<? extends CommandMessage<?>> messages) {

        return (index, command) -> {

            Object payload = command.getPayload();

            if (payload instanceof CreateAccountCommand createCmd) {

                try {
                    // Build WebClient here with load-balanced builder
                    customerWebClientBuilder.build()
                            .get()
                            .uri("lb://customer/api/customers/{customerId}",
                                    createCmd.customerId().getValue())
                            .retrieve()
                            .toBodilessEntity()
                            .block();

                    LOGGER.info("Customer validated successfully.");

                } catch (WebClientResponseException.NotFound e) {
                    throw new IllegalArgumentException(
                            "Customer not found: " + createCmd.customerId().getValue());
                }
            }

            return command;
        };
    }
}
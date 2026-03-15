package com.tongbora.accountqueryservice.message.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.config.EventProcessingConfigurer;
import org.axonframework.extensions.kafka.eventhandling.consumer.streamable.StreamableKafkaMessageSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class AxonKafkaConfig {
    public static final String PROCESSOR_NAME = "account-group";

    @Autowired
    public void configureStreamableKafkaSource(EventProcessingConfigurer eventProcessingConfigurer,
                                               StreamableKafkaMessageSource<String, byte[]> streamableKafkaMessageSource) {
        log.info("configureStreamableKafkaSource for process group: {}", PROCESSOR_NAME);
        eventProcessingConfigurer.registerPooledStreamingEventProcessor(
                PROCESSOR_NAME,
                configuration -> streamableKafkaMessageSource
        );
    }
}

//package com.tongbora.accountservice.message;
//
//import lombok.extern.slf4j.Slf4j;
//import org.axonframework.config.EventProcessingConfigurer;
//import org.axonframework.extensions.kafka.eventhandling.KafkaMessageConverter;
//import org.axonframework.extensions.kafka.eventhandling.consumer.ConsumerFactory;
//import org.axonframework.extensions.kafka.eventhandling.consumer.Fetcher;
//import org.axonframework.extensions.kafka.eventhandling.consumer.streamable.KafkaEventMessage;
//import org.axonframework.extensions.kafka.eventhandling.consumer.streamable.SortedKafkaMessageBuffer;
//import org.axonframework.extensions.kafka.eventhandling.consumer.streamable.StreamableKafkaMessageSource;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.List;
//
//@Configuration
//@Slf4j
//public class AxonKafkaConfig {
//
//    @Autowired
//    public StreamableKafkaMessageSource<String, byte[]> streamableKafkaMessageSource(List<String> topics,
//                                                                                         ConsumerFactory<String, byte[]> consumerFactory,
//                                                                                         Fetcher<String, byte[], KafkaEventMessage> fetcher,
//                                                                                         KafkaMessageConverter<String, byte[]> messageConverter,
//                                                                                         int bufferCapacity) {
//        return StreamableKafkaMessageSource.<String, byte[]>builder()
//                .topics(topics)                                                 // Defaults to a collection of "Axon.Events"
//                .consumerFactory(consumerFactory)                               // Hard requirement
//                .fetcher(fetcher)                                               // Hard requirement
//                .messageConverter(messageConverter)                             // Defaults to a "DefaultKafkaMessageConverter"
//                .bufferFactory(
//                        () -> new SortedKafkaMessageBuffer<>(bufferCapacity))   // Defaults to a "SortedKafkaMessageBuffer" with a buffer capacity of "1000"
//                .build();
//    }
//    public static final String PROCESSOR_NAME = "account-group";
//
//    @Autowired
//    public void configureStreamableKafkaSource(EventProcessingConfigurer eventProcessingConfigurer,
//                                               StreamableKafkaMessageSource<String, byte[]> streamableKafkaMessageSource) {
//        log.info("configureStreamableKafkaSource for process group: {}", PROCESSOR_NAME);
//        eventProcessingConfigurer.registerTrackingEventProcessor(
//                PROCESSOR_NAME,
//                configuration -> streamableKafkaMessageSource
//        );
//    }
//}

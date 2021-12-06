package com.example.frontservice.transport;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.stereotype.Component;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderOptions;

import java.util.HashMap;
import java.util.Map;

@Configuration
@Component
@RequiredArgsConstructor
public class KafkaProducerConfig {

    private final KafkaProperties kafkaProperties;

    @Bean
    public KafkaSender<String, Object> kafkaSender() {
        Map<String, Object> props = new HashMap<>();
        var producer = kafkaProperties.getProducer();

        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, producer.getBootstrapServers());
        props.put(ProducerConfig.CLIENT_ID_CONFIG, producer.getClientId());
        props.put(ProducerConfig.ACKS_CONFIG, producer.getAcks());
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        props.put(JsonSerializer.ADD_TYPE_INFO_HEADERS, false);

        SenderOptions<String, Object> senderOptions = SenderOptions.<String, Object>create(props).maxInFlight(1024);
        return KafkaSender.create(senderOptions);
    }
}

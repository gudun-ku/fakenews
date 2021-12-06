package com.example.frontservice.transport;

import com.example.frontservice.dto.NewsDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOptions;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Configuration
@Slf4j
public class KafkaConsumerConfig {

    private final KafkaProperties kafkaProperties;

    @Value("${news.topic}")
    private String newsTopic;

    public KafkaConsumerConfig(KafkaProperties kafkaProperties) {
        this.kafkaProperties = kafkaProperties;
    }

    @Bean
    KafkaReceiver<String, NewsDTO> newsReceiver() {
        ReceiverOptions<String, NewsDTO> options = getReceiverOptions(NewsDTO.class, newsTopic);
        return KafkaReceiver.create(options);
    }

    private <T> ReceiverOptions<String, T> getReceiverOptions(Class<T> valueType, String topic) {
        Map<String, Object> props = new HashMap<>();
        var consumer = kafkaProperties.getConsumer();

        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, consumer.getBootstrapServers());
        props.put(ConsumerConfig.CLIENT_ID_CONFIG, consumer.getClientId() + "-" + topic);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, consumer.getGroupId());
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, consumer.getAutoOffsetReset());
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, false);
        props.put(JsonDeserializer.VALUE_DEFAULT_TYPE, valueType);

        ReceiverOptions<String, T> options = ReceiverOptions.create(props);
        return options.subscription(Set.of(topic));
    }

}

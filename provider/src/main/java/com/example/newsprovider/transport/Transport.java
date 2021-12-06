package com.example.newsprovider.transport;

import com.example.newsprovider.dto.NewsDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderRecord;

@Slf4j
@Component
public class Transport {

    private final KafkaSender<String, Object> kafkaSender;

    public Transport(KafkaSender<String, Object> kafkaSender) {
        this.kafkaSender = kafkaSender;
    }

    @Value("${news.transport.topic}")
    private String newsTopic;

    public Mono<String> publishNews(NewsDTO message) {
        var key = message.getId().toString();
        var metadata = message.getTitle();

        return kafkaSender
                .send(Mono
                        .just(SenderRecord.create(new ProducerRecord<>(newsTopic, key, message), metadata)))
                .doOnError(e ->
                        log.error("Не удалось отправить новость в топик: {}", newsTopic, e)
                )
                .map(senderResult -> {
                    log.info("Успешно отправлено в топик: {} новость: {}",
                            senderResult.recordMetadata().topic(),
                            metadata);
                    return senderResult.correlationMetadata();
                })
                .single();
    }
}

package com.example.frontservice.transport;

import com.example.frontservice.dto.NewsDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverRecord;


@Component
@Slf4j
public class KafkaNewsConsumer {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private final KafkaReceiver<String, NewsDTO> kafkaReceiver;

    public KafkaNewsConsumer(KafkaReceiver<String, NewsDTO> kafkaReceiver) {
        this.kafkaReceiver = kafkaReceiver;
    }

    public Flux<NewsDTO> consumeMessageStream() {
        return kafkaReceiver.receive()
                .flatMap(this::processRecords);
    }

    private Mono<NewsDTO> processRecords(ReceiverRecord<String, NewsDTO> r) {
        NewsDTO news = r.value();
        news.setContent(news.getContent());
        news.setTitle(news.getTitle());
        return Mono.just(news);
    }

    public void consumeMessages() {
        kafkaReceiver.receive()
            //  .filter(r -> r.value().getRequester() == null) // если нужно разделить на вновь поступающие и из базы
                .doOnNext(r -> {
                    processRecord(r);
                    r.receiverOffset().acknowledge();
                })
                .onErrorContinue((e, o) -> log.error("Ошибка обработки сообщения: ", e))
                .subscribe();
    }

    private void processRecord(ReceiverRecord<String, NewsDTO> r) {
        NewsDTO news = r.value();
        news.setContent(news.getContent());
        news.setTitle(news.getTitle());

        try {
            String json = MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(news);
            log.info("Received message:\n {}", json);
        } catch (JsonProcessingException e) {
            log.error("Error processing record:", e);
        }
    }
}

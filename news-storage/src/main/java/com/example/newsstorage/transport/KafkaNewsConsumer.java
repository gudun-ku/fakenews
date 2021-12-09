package com.example.newsstorage.transport;

import com.example.newsstorage.dto.NewsDTO;
import com.example.newsstorage.entity.News;
import com.example.newsstorage.service.NewsService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverRecord;

import java.util.UUID;


@Component
@Slf4j
public class KafkaNewsConsumer {

    /*
    Настраиваем Mapper для корректной работы с OffsetDateTime
     */
    private static final ObjectMapper MAPPER = JsonMapper.builder()
            .addModule(new JavaTimeModule())
            .build();
    ;

    private final KafkaReceiver<String, NewsDTO> kafkaReceiver;
    private final NewsService newsService;

    public KafkaNewsConsumer(KafkaReceiver<String, NewsDTO> kafkaReceiver, NewsService newsService) {
        this.kafkaReceiver = kafkaReceiver;
        this.newsService = newsService;
    }

    public void consumeMessages() {
        kafkaReceiver.receive()
                /*
                Фильтруем записи по отсутствию внутреннего идентификатора (internalId == null)
                 */
                .filter(r -> r.value().getInternalId() == null)
                .doOnNext(r -> {
                    processRecord(r);
                    r.receiverOffset().acknowledge();
                })
                .onErrorContinue((e, o) -> log.error("Ошибка обработки сообщения: ", e))
                .subscribe();
    }

    private void processRecord(ReceiverRecord<String, NewsDTO> r) {
        NewsDTO dto = r.value();
        News news = new News();
        news.setUuid(dto.getId());
        news.setTitle(dto.getTitle());
        news.setContent(dto.getContent());
        news.setPubDate(dto.getPubDate());
        /*
        При записи в базу устанавливаем internalId = new UUID()
         */
        news.setInternalId(UUID.randomUUID());


        newsService.create(news);
        try {
            String json = MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(news);
            log.info("Принято сообщение от kafka:\n {}", json);
        } catch (JsonProcessingException e) {
            log.error("Ошибка обработки сообщения: ", e);
        }
    }
}

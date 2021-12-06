package com.example.frontservice.rest;

import com.example.frontservice.dto.NewsDTO;
import com.example.frontservice.transport.KafkaNewsConsumer;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
public class NewsHandler {

    private final KafkaNewsConsumer consumer;
    private final NewsService service;

    public NewsHandler(KafkaNewsConsumer consumer, NewsService service) {
        this.consumer = consumer;
        this.service = service;
    }

    public Mono<ServerResponse> streamNews(ServerRequest request) {
        return ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(consumer.consumeMessageStream(), NewsDTO.class);
    }

    public Mono<ServerResponse> allNews(ServerRequest request) {
        return service.publishAllNews()
                .flatMap(message -> ok().contentType(APPLICATION_JSON).bodyValue(message))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

}

package com.example.newsprovider.client;

import com.example.newsprovider.dto.SourceNewsDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class NewsClient {

    private final WebClient webClient;

    @Autowired
    public NewsClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<SourceNewsDTO> getLatestNews() {

        log.debug("getting latest news... ");
        return webClient
                .get()
                .uri("/v1/news")
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .exchangeToMono(clientResponse -> clientResponse.bodyToMono(SourceNewsDTO.class))
                .filter(dto -> dto.getId() != null)
                .switchIfEmpty(Mono.error(new RuntimeException("Не могу получить новости")));
    }
}



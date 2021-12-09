package com.example.frontservice.rest;

import com.example.frontservice.dto.NewsDTO;
import com.example.frontservice.entity.News;
import com.example.frontservice.repository.NewsRepository;
import com.example.frontservice.transport.KafkaNewsProducer;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Comparator;
import java.util.stream.Collectors;

@Service
public class NewsService {

    private final NewsRepository repository;
    private final KafkaNewsProducer producer;

    public NewsService(NewsRepository repository, KafkaNewsProducer producer) {
        this.repository = repository;
        this.producer = producer;
    }

    public Mono<Integer> publishAllNews() {
        /*
        Здесь тоже можно было вывести в mapper сервис, но для упрощения прямо в паблишере
         */
        var newsToSend =  repository.findAll()
                .stream().sorted(Comparator.comparing(News::getPubDate))
                .map(news -> new NewsDTO(news.getUuid(),
                        news.getInternalId(),
                        news.getTitle(),
                        news.getContent(),
                        news.getPubDate()))
                .collect(Collectors.toUnmodifiableList());

        newsToSend.forEach(newsDTO -> producer.publishNews(newsDTO).subscribe());

        return Mono.just(newsToSend.size());
    }
}

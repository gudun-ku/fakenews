package com.example.newssource.service;

import com.example.newssource.dto.News;
import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
public class FakeNewsService {

    final Faker faker = new Faker();

    public News generateNews() {
        log.info("Generated news!");
        return News.builder()
                .id(faker.idNumber().toString())
                .title(faker.company().industry())
                .flash(faker.random().nextBoolean())
                .content(faker.company().buzzword())
                .pubDate(LocalDateTime.now())
                .build();
    }
}

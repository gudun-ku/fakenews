package com.example.newsstorage.service;

import com.example.newsstorage.entity.News;
import com.example.newsstorage.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class NewsService {

    private final NewsRepository newsRepository;

    public NewsService(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

     public News create(News news) {
        News created = newsRepository.save(news);
        log.info("Saved news to database with id={}", created);
        return created;
    }
}

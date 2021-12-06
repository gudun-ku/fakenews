package com.example.newsprovider.task;

import com.example.newsprovider.service.NewsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@EnableScheduling
@RequiredArgsConstructor
public class GetNewsTask {

    private final NewsService newsService;

    //@Scheduled(cron = "${ms.properties.cron}")
    @Scheduled(fixedRate = 10000)
    public void run() {
        newsService.getAndPublishNews();
    }
}
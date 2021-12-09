package com.example.newsprovider.task;

import com.example.newsprovider.annotation.Clustered;
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


    @Clustered
    @Scheduled(cron = "${ms.properties.cron}")
    public void run() {
        newsService.getAndPublishNews();
    }
}
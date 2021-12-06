package com.example.newsprovider.service;

import com.example.newsprovider.client.NewsClient;
import com.example.newsprovider.mapper.NewsMapper;
import com.example.newsprovider.transport.Transport;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class NewsService {

    private final NewsClient client;
    private final Transport transport;

    public void getAndPublishNews() {

        client.getLatestNews()
                .map(NewsMapper::sourceToSink)
                .flatMap(transport::publishNews)
                .subscribe(message -> log.info("Новость {} отправлена", message));
    }
}

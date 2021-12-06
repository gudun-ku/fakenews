package com.example.newssource.controller;

import com.example.newssource.dto.News;
import com.example.newssource.service.FakeNewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/news")
public class FakeNewsController {

    private final FakeNewsService service;

    @GetMapping
    public News getNews() {
        return service.generateNews();
    }
}

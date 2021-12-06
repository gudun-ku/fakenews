package com.example.newssource.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder(toBuilder = true)
public class News {
    private String id;
    private String title;
    private Boolean flash;
    private String content;
    private LocalDateTime pubDate;
}

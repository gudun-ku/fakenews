package com.example.newsprovider.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SourceNewsDTO {
    private String id;
    private String title;
    private Boolean flash;
    private String content;
    private LocalDateTime pubDate;
}

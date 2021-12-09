package com.example.newsstorage.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
public class NewsDTO {
    private UUID id;
    private UUID internalId;
    private String title;
    private String content;
    private OffsetDateTime pubDate;
}

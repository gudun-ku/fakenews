package com.example.frontservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewsDTO {
    private UUID id;
    private UUID internalId;
    private String title;
    private String content;
    private OffsetDateTime pubDate;
}

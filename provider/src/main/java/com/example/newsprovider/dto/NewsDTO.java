package com.example.newsprovider.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Builder(toBuilder = true)
public class NewsDTO {
    private UUID id;
    private String title;
    private String content;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private OffsetDateTime pubDate;
}

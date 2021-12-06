package com.example.newsprovider.mapper;

import com.example.newsprovider.dto.NewsDTO;
import com.example.newsprovider.dto.SourceNewsDTO;

import java.nio.charset.StandardCharsets;
import java.time.ZoneOffset;
import java.util.UUID;

public class NewsMapper {

    public  static NewsDTO sourceToSink(SourceNewsDTO source) {
        return NewsDTO.builder()
                .id(UUID.nameUUIDFromBytes((source.getId() + source.getTitle()).getBytes(StandardCharsets.UTF_8)))
                .title(source.getFlash() ? source.getTitle().toUpperCase() : source.getTitle())
                .content(source.getContent())
                .pubDate(source.getPubDate().atOffset(ZoneOffset.UTC)) //все храним в UTC
                .build();

    }
}

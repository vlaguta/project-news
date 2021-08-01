package com.example.testtask.service.converter;

import com.example.testtask.controller.dto.NewsCreateRequest;
import com.example.testtask.controller.dto.NewsDto;
import com.example.testtask.repository.entity.NewsEntity;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class NewsConverter {

    public static NewsDto convertNewsEntityToNewsDto(NewsEntity news) {

        return NewsDto.builder()
                .title(news.getTitle())
                .date(news.getDate())
                .text(news.getText())
                .comments(news.getComments()
                        .stream()
                        .map(CommentConverter::convertCommentEntityToCommentDto)
                        .collect(Collectors.toList()))
                .build();
    }

    public static NewsEntity convertNewsCreateRequestToNewsEntity(NewsCreateRequest news) {

        return NewsEntity.builder()
                .title(news.getTitle())
                .text(news.getText())
                .build();
    }
}

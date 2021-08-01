package com.example.testtask.service.converter;

import com.example.testtask.controller.dto.CommentDto;
import com.example.testtask.controller.dto.NewsCreateRequest;
import com.example.testtask.controller.dto.NewsDto;
import com.example.testtask.repository.entity.CommentEntity;
import com.example.testtask.repository.entity.NewsEntity;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NewsConverterTest {

    @Test
    void convertNewsEntityToNewsDtoSuccessfullyTest() {

        //given
        CommentEntity commentEntity = CommentEntity.builder()
                .id(1L)
                .username("user")
                .text("test")
                .date(LocalDateTime.MAX)
                .build();

        NewsEntity newsEntity = NewsEntity.builder()
                .id(1L)
                .title("title")
                .text("text")
                .comments(List.of(commentEntity))
                .build();

        CommentDto commentDto = CommentDto.builder()
                .username("user")
                .text("test")
                .date(LocalDateTime.MAX)
                .newsId(1L)
                .build();

        commentEntity.setNews(newsEntity);

        //when
        NewsDto actualNewsDto = NewsConverter.convertNewsEntityToNewsDto(newsEntity);

        //then
        NewsDto expectedNewsDto = NewsDto.builder()
                .title("title")
                .text("text")
                .comments(List.of(commentDto))
                .build();

        assertEquals(expectedNewsDto, actualNewsDto);
    }

    @Test
    void convertCommentCreateRequestToCommentEntitySuccessfullyTest() {

        //given
        NewsCreateRequest newsCreateRequest = NewsCreateRequest.builder()
                .title("title")
                .text("text")
                .build();

        CommentEntity commentEntity = CommentEntity.builder()
                .id(1L)
                .username("user")
                .text("test")
                .date(LocalDateTime.now())
                .build();

        NewsEntity newsEntity = NewsEntity.builder()
                .id(1L)
                .title("title")
                .text("text")
                .comments(List.of(commentEntity))
                .build();

        commentEntity.setNews(newsEntity);

        //when
        NewsEntity actualNewsEntity = NewsConverter.convertNewsCreateRequestToNewsEntity(newsCreateRequest);

        //then
        NewsEntity expectedNewsEntity = NewsEntity.builder()
                .title("title")
                .text("text")
                .build();

        assertEquals(expectedNewsEntity, actualNewsEntity);
    }
}

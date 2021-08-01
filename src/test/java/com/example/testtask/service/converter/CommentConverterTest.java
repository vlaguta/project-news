package com.example.testtask.service.converter;

import com.example.testtask.controller.dto.CommentCreateRequest;
import com.example.testtask.controller.dto.CommentDto;
import com.example.testtask.repository.entity.CommentEntity;
import com.example.testtask.repository.entity.NewsEntity;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommentConverterTest {

    @Test
    void convertCommentEntityToCommentDtoSuccessfullyTest() {

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

        commentEntity.setNews(newsEntity);

        //when
        CommentDto actualCommentDto = CommentConverter.convertCommentEntityToCommentDto(commentEntity);

        //then
        CommentDto expectedCommentDto = CommentDto.builder()
                .username("user")
                .text("test")
                .date(LocalDateTime.MAX)
                .newsId(1L)
                .build();

        assertEquals(expectedCommentDto, actualCommentDto);
    }

    @Test
    void convertCommentCreateRequestToCommentEntitySuccessfullyTest() {

        //given
        CommentCreateRequest commentCreateRequest = CommentCreateRequest.builder()
                .username("user")
                .text("test")
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
        CommentEntity actualCommentEntity = CommentConverter.convertCommentCreateRequestToCommentEntity(commentCreateRequest);

        //then
        CommentEntity expectedCommentEntity = CommentEntity.builder()
                .username("user")
                .text("test")
                .build();

        assertEquals(expectedCommentEntity, actualCommentEntity);
    }


}

package com.example.testtask.service.converter;

import com.example.testtask.controller.dto.CommentCreateRequest;
import com.example.testtask.controller.dto.CommentDto;
import com.example.testtask.repository.entity.CommentEntity;
import org.springframework.stereotype.Component;

@Component
public class CommentConverter {

    public static CommentDto convertCommentEntityToCommentDto(CommentEntity commentEntity) {

        return CommentDto.builder()
                .date(commentEntity.getDate())
                .text(commentEntity.getText())
                .username(commentEntity.getUsername())
                .newsId(commentEntity.getNews().getId())
                .build();
    }

    public static CommentEntity convertCommentCreateRequestToCommentEntity(CommentCreateRequest commentCreateRequest) {

        return CommentEntity.builder()
                .text(commentCreateRequest.getText())
                .username(commentCreateRequest.getUsername())
                .build();
    }
}

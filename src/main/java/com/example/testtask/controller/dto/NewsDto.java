package com.example.testtask.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewsDto {

    private String title;
    private String text;
    private LocalDateTime date;
    private List<CommentDto> comments;
}

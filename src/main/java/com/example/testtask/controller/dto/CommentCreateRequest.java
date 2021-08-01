package com.example.testtask.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentCreateRequest {

    @NotBlank
    @Size(min = 1, max = 2000)
    private String text;
    @NotBlank
    @Size(min = 4, max = 20)
    private String username;
    private long newsId;
}

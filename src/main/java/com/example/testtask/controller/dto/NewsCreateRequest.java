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
public class NewsCreateRequest {

    @NotBlank
    @Size(min = 1, max = 100)
    private String title;
    @Size(min = 1, max = 5000)
    private String text;
}

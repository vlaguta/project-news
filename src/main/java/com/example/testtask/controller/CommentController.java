package com.example.testtask.controller;

import com.example.testtask.controller.dto.CommentCreateRequest;
import com.example.testtask.controller.dto.CommentDto;
import com.example.testtask.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping()
    public void create(@RequestBody @Valid CommentCreateRequest commentCreateRequest) {
        commentService.create(commentCreateRequest);
    }

    @PutMapping("/{id}")
    public void update(
            @PathVariable long id,
            @RequestBody CommentDto commentDto
    ) {
        commentService.update(id, commentDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        commentService.delete(id);
    }
}

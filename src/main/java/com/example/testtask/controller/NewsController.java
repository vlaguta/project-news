package com.example.testtask.controller;

import com.example.testtask.controller.dto.NewsCreateRequest;
import com.example.testtask.controller.dto.NewsDto;
import com.example.testtask.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/news")
@RequiredArgsConstructor
public class NewsController {

    private final NewsService newsService;

    @GetMapping("/{id}")
    public NewsDto get(

            @PathVariable long id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "2") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);

        return newsService.getNewsWithPageableComments(id, pageable);
    }

    @GetMapping
    public List<NewsDto> getAll(

            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);

        return newsService.getAll(pageable);
    }

    @GetMapping("/find")
    public List<NewsDto> findByText(@RequestParam String search) {
        return newsService.getNewsByFullTextSearch(search);
    }

    @PostMapping
    public void create(@RequestBody @Valid NewsCreateRequest newsCreateRequest) {
        newsService.create(newsCreateRequest);
    }

    @PutMapping("/{id}")
    public void update(

            @PathVariable long id,
            @RequestBody NewsDto newsDto
    ) {

        newsService.update(id, newsDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        newsService.delete(id);
    }

}

package com.example.testtask.service;

import com.example.testtask.controller.dto.CommentDto;
import com.example.testtask.controller.dto.NewsCreateRequest;
import com.example.testtask.controller.dto.NewsDto;
import com.example.testtask.exception.BusinessException;
import com.example.testtask.repository.NewsRepository;
import com.example.testtask.repository.entity.NewsEntity;
import com.example.testtask.service.converter.NewsConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {

    private final NewsRepository newsRepository;
    private final CommentService commentService;
    private final Clock clock;

    @Override
    public void create(NewsCreateRequest newsCreateRequest) {

        log.info("Saving a news with title: " + newsCreateRequest.getTitle());

        NewsEntity newsEntity = NewsConverter.convertNewsCreateRequestToNewsEntity(newsCreateRequest);
        newsEntity.setDate(LocalDateTime.now(clock));

        newsRepository.save(newsEntity);
    }

    @Override
    public void delete(long id) {

        log.info("Deleting a news with id: " + id);

        NewsEntity newsEntity = newsRepository
                .findById(id)
                .orElseThrow(() -> new BusinessException("News not found with id: " + id, HttpStatus.NOT_FOUND.value()));
        newsRepository.delete(newsEntity);
    }

    @Override
    public void update(long id, NewsDto newsDto) {

        log.info("Update a news with id:" + id);

        NewsEntity newsEntity = newsRepository.findById(id)
                .orElseThrow(() -> new BusinessException("News not found with id: " + id, HttpStatus.NOT_FOUND.value()));

        if (newsDto.getTitle() != null) {
            newsEntity.setTitle(newsDto.getTitle());
        }
        if (newsDto.getText() != null) {
            newsEntity.setText(newsDto.getText());
        }

        newsEntity.setDate(LocalDateTime.now(clock));

        newsRepository.save(newsEntity);
    }

    @Override
    public List<NewsDto> getAll(Pageable pageable) {

        log.info("Getting all news with page-by-page view");

        return newsRepository.findAll(pageable)
                .stream()
                .map(NewsConverter::convertNewsEntityToNewsDto)
                .collect(Collectors.toList());
    }

    @Override
    public NewsDto getNewsWithPageableComments(long id, Pageable pageable) {

        log.info("Getting news with comments with page-by-page view. News id: " + id);

        NewsEntity news = newsRepository.findById(id)
                .orElseThrow(() -> new BusinessException("News not found", HttpStatus.NOT_FOUND.value()));

        List<CommentDto> pageableComments = commentService.getAllWithPagination(pageable, news);
        NewsDto newsDto = NewsConverter.convertNewsEntityToNewsDto(news);

        newsDto.setComments(pageableComments);
        return newsDto;
    }

    @Override
    public List<NewsDto> getNewsByFullTextSearch(String text) {

        log.info("Getting news by full text search");

        return newsRepository.search(text)
                .stream()
                .map(NewsConverter::convertNewsEntityToNewsDto)
                .collect(Collectors.toList());
    }
}

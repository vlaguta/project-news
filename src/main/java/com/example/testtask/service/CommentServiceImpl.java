package com.example.testtask.service;

import com.example.testtask.controller.dto.CommentCreateRequest;
import com.example.testtask.controller.dto.CommentDto;
import com.example.testtask.exception.BusinessException;
import com.example.testtask.repository.CommentRepository;
import com.example.testtask.repository.NewsRepository;
import com.example.testtask.repository.entity.CommentEntity;
import com.example.testtask.repository.entity.NewsEntity;
import com.example.testtask.service.converter.CommentConverter;
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
public class CommentServiceImpl implements CommentService {

    private final NewsRepository newsRepository;
    private final CommentRepository commentRepository;
    private final Clock clock;

    @Override
    public void create(CommentCreateRequest commentCreateRequest) {

        log.info("Saving a comment from a user " + commentCreateRequest.getUsername() + "for news id: " + commentCreateRequest.getNewsId());

        long newsId = commentCreateRequest.getNewsId();

        NewsEntity newsEntity = newsRepository.findById(newsId)
                .orElseThrow(() -> new BusinessException("News not found with id" + newsId, HttpStatus.INTERNAL_SERVER_ERROR.value()));
        CommentEntity commentEntity = CommentConverter.convertCommentCreateRequestToCommentEntity(commentCreateRequest);
        commentEntity.setNews(newsEntity);
        commentEntity.setDate(LocalDateTime.now(clock));
        commentRepository.save(commentEntity);
    }

    @Override
    public void delete(long id) {

        log.info("Deleting a comment with id: " + id);

        CommentEntity commentEntity = commentRepository
                .findById(id)
                .orElseThrow(() -> new BusinessException("Comment not found", HttpStatus.NOT_FOUND.value()));
        commentRepository.delete(commentEntity);
    }

    @Override
    public void update(long id, CommentDto commentDto) {

        log.info("Update a comment with id: " + id);

        CommentEntity commentEntity = commentRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Comment not found", HttpStatus.NOT_FOUND.value()));

        if (commentDto.getText() != null) {
            commentEntity.setText(commentDto.getText());
        }
        if (commentDto.getUsername() != null) {
            commentEntity.setUsername(commentDto.getUsername());
        }

        commentEntity.setDate(LocalDateTime.now(clock));

        commentRepository.save(commentEntity);
    }

    @Override
    public List<CommentDto> getAllWithPagination(Pageable pageable, NewsEntity news) {

        log.info("Getting comments with page-by-page view for news with id: " + news.getId());

        return commentRepository.findAllByNews(pageable, news)
                .stream()
                .map(CommentConverter::convertCommentEntityToCommentDto)
                .collect(Collectors.toList());
    }
}

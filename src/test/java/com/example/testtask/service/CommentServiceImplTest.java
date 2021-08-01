package com.example.testtask.service;

import com.example.testtask.configuration.TestTimeConfiguration;
import com.example.testtask.controller.dto.CommentCreateRequest;
import com.example.testtask.controller.dto.CommentDto;
import com.example.testtask.exception.BusinessException;
import com.example.testtask.repository.CommentRepository;
import com.example.testtask.repository.NewsRepository;
import com.example.testtask.repository.entity.CommentEntity;
import com.example.testtask.repository.entity.NewsEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = CommentServiceImpl.class)
@ContextConfiguration(
        classes = {TestTimeConfiguration.class})
class CommentServiceImplTest {

    @Autowired
    private CommentService commentService;

    @Autowired
    private Clock clock;

    @MockBean
    private CommentRepository commentRepository;

    @MockBean
    private NewsRepository newsRepository;

    @Test
    void updateCommentByIdSuccessfullyTest() {

        //given:
        long existedCommentId = 1L;

        CommentDto commentDto = CommentDto.builder()
                .text("updatedTest")
                .newsId(1L)
                .date(LocalDateTime.now(clock))
                .build();

        CommentEntity commentEntity = CommentEntity.builder()
                .id(1L)
                .username("123")
                .text("initialText")
                .date(LocalDateTime.now(clock))
                .build();

        when(commentRepository.findById(existedCommentId))
                .thenReturn(Optional.of(commentEntity));

        // when
        commentService.update(existedCommentId, commentDto);

        // then
        CommentEntity expectedCommentEntity = CommentEntity.builder()
                .id(1L)
                .username("123")
                .text("updatedTest")
                .date(LocalDateTime.now(clock))
                .build();

        verify(commentRepository).save(expectedCommentEntity);
    }

    @Test
    @DisplayName("Try to update comment by id and throw business error when comment not found")
    void updateCommentByIdCommentNotFoundTest() {

        //given
        long notExistedCommentId = 1L;

        CommentDto commentDto = CommentDto.builder()
                .text("updatedTest")
                .newsId(1L)
                .date(LocalDateTime.now(clock))
                .build();

        when(commentRepository.findById(notExistedCommentId))
                .thenReturn(Optional.empty());

        //when
        BusinessException businessException = assertThrows(
                BusinessException.class,
                () -> commentService.update(notExistedCommentId, commentDto)
        );

        //then
        String expectedErrorMessage = "Comment not found";
        assertEquals(expectedErrorMessage, businessException.getMessage());
    }

    @Test
    void updateCommentByIdCommentWhenTextAndUsernameNull() {

        //given
        long commentId = 1L;

        CommentDto commentDto = CommentDto.builder()
                .text(null)
                .username(null)
                .newsId(1L)
                .date(LocalDateTime.now(clock))
                .build();

        CommentEntity commentEntity = CommentEntity.builder()
                .id(1L)
                .username("user")
                .text("test")
                .date(LocalDateTime.now(clock))
                .build();

        when(commentRepository.findById(commentId))
                .thenReturn(Optional.of(commentEntity));

        //when

        commentService.update(commentId, commentDto);

        //then
        CommentEntity expectedCommentEntity = CommentEntity.builder()
                .id(1L)
                .username("user")
                .text("test")
                .date(LocalDateTime.now(clock))
                .build();

        verify(commentRepository).save(expectedCommentEntity);
    }

    @Test
    void createCommentSuccessfullyTest() {

        //given
        CommentCreateRequest commentCreateRequest = CommentCreateRequest.builder()
                .username("user")
                .text("createRequestTest")
                .newsId(1L)
                .build();

        NewsEntity newsEntity = NewsEntity.builder()
                .id(1L)
                .title("title")
                .text("text")
                .date(LocalDateTime.now(clock))
                .comments(Collections.emptyList())
                .build();

        when(newsRepository.findById(commentCreateRequest.getNewsId()))
                .thenReturn(Optional.of(newsEntity));
        //when
        commentService.create(commentCreateRequest);

        //then
        CommentEntity expectedCommentEntity = CommentEntity.builder()
                .username("user")
                .text("createRequestTest")
                .date(LocalDateTime.now(clock))
                .news(newsEntity)
                .build();

        verify(commentRepository).save(expectedCommentEntity);

    }

    @Test
    @DisplayName("Try to create comment and throw business error when news not found")
    void createCommentWhenNewsNotFoundTest() {

        //given
        CommentCreateRequest commentCreateRequest = CommentCreateRequest.builder()
                .username("user")
                .text("createRequestTest")
                .newsId(1L)
                .build();

        when(newsRepository.findById(commentCreateRequest.getNewsId()))
                .thenReturn(Optional.empty());


        //when
        BusinessException businessException = assertThrows(
                BusinessException.class,
                () -> commentService.create(commentCreateRequest)
        );

        //then
        String expectedErrorMessage = "News not found";
        assertEquals(expectedErrorMessage, businessException.getMessage());

    }

    @Test
    void deleteCommentSuccessfulTest() {
        //given
        long expectedCommentId = 1L;

        CommentEntity commentEntity = CommentEntity.builder()
                .id(1L)
                .text("text")
                .username("username")
                .date(LocalDateTime.now(clock))
                .build();

        when(commentRepository.findById(expectedCommentId))
                .thenReturn(Optional.of(commentEntity));
        //when
        commentService.delete(expectedCommentId);
        //then

        CommentEntity expectedCommentEntity = CommentEntity.builder()
                .id(1L)
                .text("text")
                .date(LocalDateTime.now(clock))
                .username("username")
                .build();

        verify(commentRepository).delete(expectedCommentEntity);
    }

    @Test
    @DisplayName("Try to delete comment by id and throw business error when comment not found")
    void deleteCommentByIdCommentNotFoundTest() {
        //given
        long expectedCommentId = 1L;

        when(commentRepository.findById(expectedCommentId))
                .thenReturn(Optional.empty());
        //when
        BusinessException businessException = assertThrows(
                BusinessException.class,
                () -> commentService.delete(expectedCommentId)
        );
        //then
        String expectedErrorMessage = "Comment not found";
        assertEquals(expectedErrorMessage, businessException.getMessage());
    }

    @Test
    void getAllCommentSuccessfulTest() {
        //given
        int size = 1;
        int page = 1;
        Pageable pageable = PageRequest.of(size, page);

        NewsEntity newsEntity = NewsEntity.builder()
                .id(1L)
                .title("title")
                .text("text")
                .date(LocalDateTime.now(clock))
                .build();

        CommentEntity commentEntity = CommentEntity.builder()
                .id(1L)
                .username("user")
                .text("test")
                .date(LocalDateTime.now(clock))
                .news(newsEntity)
                .build();

        Page<CommentEntity> pages = new PageImpl<>(List.of(commentEntity));

        when(commentRepository.findAllByNews(pageable, newsEntity))
                .thenReturn(pages);

        //when
        List<CommentDto> actualCommentsDto = commentService.getAllWithPagination(pageable, newsEntity);

        //then
       List<CommentDto> expectedCommentsDto = List.of(CommentDto.builder()
                .newsId(1L)
                .username("user")
                .text("test")
                .date(LocalDateTime.now(clock))
                .build());

        assertEquals(expectedCommentsDto, actualCommentsDto);
    }
}

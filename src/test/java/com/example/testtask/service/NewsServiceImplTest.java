package com.example.testtask.service;

import com.example.testtask.configuration.TestTimeConfiguration;
import com.example.testtask.controller.dto.CommentDto;
import com.example.testtask.controller.dto.NewsCreateRequest;
import com.example.testtask.controller.dto.NewsDto;
import com.example.testtask.exception.BusinessException;
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
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = NewsServiceImpl.class)
@ContextConfiguration(
        classes = {TestTimeConfiguration.class})
class NewsServiceImplTest {

    @Autowired
    Clock clock;
    @Autowired
    private NewsService newsService;
    @MockBean
    private NewsRepository newsRepository;

    @MockBean
    private CommentService commentService;

    @Test
    void updateNewsByIdSuccessfullyTest() {

        //given:
        long existedNewsId = 1L;

        NewsDto newsDto = NewsDto.builder()
                .title("updatedTitle")
                .text("updatedNews")
                .date(LocalDateTime.now(clock))
                .build();

        NewsEntity newsEntity = NewsEntity.builder()
                .id(1L)
                .title("initialTitle")
                .text("initialNews")
                .date(LocalDateTime.now(clock))
                .build();

        when(newsRepository.findById(existedNewsId))
                .thenReturn(Optional.of(newsEntity));

        // when
        newsService.update(existedNewsId, newsDto);

        // then
        NewsEntity expectedNewsEntity = NewsEntity.builder()
                .id(1L)
                .title("updatedTitle")
                .text("updatedNews")
                .date(LocalDateTime.now(clock))
                .build();

        verify(newsRepository).save(expectedNewsEntity);
    }

    @Test
    @DisplayName("Try to update news by id and throw business error when news not found")
    void updateNewsByIdNotFoundTest() {

        //given
        long notExistedNewsId = 1L;

        NewsDto newsDto = NewsDto.builder()
                .title("title")
                .text("text")
                .date(LocalDateTime.now(clock))
                .build();

        when(newsRepository.findById(notExistedNewsId))
                .thenReturn(Optional.empty());

        //when
        BusinessException businessException = assertThrows(
                BusinessException.class,
                () -> newsService.update(notExistedNewsId, newsDto)
        );

        //then
        String expectedErrorMessage = "News not found";
        assertEquals(expectedErrorMessage, businessException.getMessage());
    }

    @Test
    void updateNewsByIdNewsWhenTextAndTitleNull() {

        //given
        long newsId = 1L;

        NewsDto newsDto = NewsDto.builder()
                .title(null)
                .text(null)
                .date(LocalDateTime.now(clock))
                .build();

        NewsEntity newsEntity = NewsEntity.builder()
                .id(1L)
                .title("title")
                .text("text")
                .date(LocalDateTime.now(clock))
                .build();

        when(newsRepository.findById(newsId))
                .thenReturn(Optional.of(newsEntity));

        //when
        newsService.update(newsId, newsDto);

        //then
        NewsEntity expectedNewsEntity = NewsEntity.builder()
                .id(1L)
                .title("title")
                .text("text")
                .date(LocalDateTime.now(clock))
                .build();

        verify(newsRepository).save(expectedNewsEntity);
    }

    @Test
    void createCommentSuccessfullyTest() {

        //given

        NewsCreateRequest newsCreateRequest = NewsCreateRequest.builder()
                .title("title")
                .text("createRequestTest")
                .build();

        //when
        newsService.create(newsCreateRequest);

        //then
        NewsEntity expectedNewsEntity = NewsEntity.builder()
                .title("title")
                .text("createRequestTest")
                .date(LocalDateTime.now(clock))
                .build();

        verify(newsRepository).save(expectedNewsEntity);
    }

    @Test
    void deleteNewsSuccessfulTest() {
        //given
        long expectedNewsId = 1L;

        NewsEntity newsEntity = NewsEntity.builder()
                .id(1L)
                .title("title")
                .text("text")
                .date(LocalDateTime.now(clock))
                .build();

        when(newsRepository.findById(expectedNewsId))
                .thenReturn(Optional.of(newsEntity));
        //when
        newsService.delete(expectedNewsId);
        //then

        NewsEntity expectedNewsEntity = NewsEntity.builder()
                .id(1L)
                .title("title")
                .text("text")
                .date(LocalDateTime.now(clock))
                .build();

        verify(newsRepository).delete(expectedNewsEntity);
    }

    @Test
    @DisplayName("Try to delete news by id and throw business error when news not found")
    void deleteNewsByIdNewsNotFoundTest() {
        //given
        long expectedNewsId = 1L;

        when(newsRepository.findById(expectedNewsId))
                .thenReturn(Optional.empty());
        //when
        BusinessException businessException = assertThrows(
                BusinessException.class,
                () -> newsService.delete(expectedNewsId)
        );
        //then
        String expectedErrorMessage = "News not found";
        assertEquals(expectedErrorMessage, businessException.getMessage());
    }

    @Test
    void getAllNewsSuccessfulTest() {
        //given
        int size = 1;
        int page = 1;
        Pageable pageable = PageRequest.of(size, page);

        CommentDto commentDto = CommentDto.builder()
                .username("user")
                .text("test")
                .date(LocalDateTime.now(clock))
                .newsId(1L)
                .build();

        CommentEntity commentEntity = CommentEntity.builder()
                .id(1L)
                .username("user")
                .text("test")
                .date(LocalDateTime.now(clock))
                .build();

        NewsEntity newsEntity = NewsEntity.builder()
                .id(1L)
                .title("title")
                .text("text")
                .date(LocalDateTime.now(clock))
                .comments(List.of(commentEntity))
                .build();

        commentEntity.setNews(newsEntity);

        Page<NewsEntity> pages = new PageImpl<>(List.of(newsEntity));

        when(newsRepository.findAll(pageable))
                .thenReturn(pages);
        when(commentService.getAllWithPagination(pageable,newsEntity))
                .thenReturn(List.of(commentDto));

        //when
        List<NewsDto> actualNewsDto = newsService.getAll(pageable);

        //then
        List<NewsDto> expectedNewsDto = List.of(NewsDto.builder()
                .title("title")
                .text("text")
                .date(LocalDateTime.now(clock))
                .comments(List.of(commentDto))
                .build());

        assertEquals(expectedNewsDto, actualNewsDto);
    }

    @Test
    void getNewsWithPageableCommentSuccessfulTest() {
        //given
        int size = 1;
        int page = 1;
        long expectedNewsId = 1L;

        Pageable pageable = PageRequest.of(size, page);

        CommentDto commentDto = CommentDto.builder()
                .username("user")
                .text("test")
                .date(LocalDateTime.now(clock))
                .newsId(1L)
                .build();

        CommentEntity commentEntity = CommentEntity.builder()
                .id(1L)
                .username("user")
                .text("test")
                .date(LocalDateTime.now(clock))
                .build();

        NewsEntity newsEntity = NewsEntity.builder()
                .id(1L)
                .title("title")
                .text("text")
                .date(LocalDateTime.now(clock))
                .comments(List.of(commentEntity))
                .build();

        commentEntity.setNews(newsEntity);

        when(newsRepository.findById(expectedNewsId))
                .thenReturn(Optional.of(newsEntity));

        //when
        NewsDto actualNewsDto = newsService.getNewsWithPageableComments(expectedNewsId, pageable);

        //then
        NewsDto expectedNewsDto = NewsDto.builder()
                .title("title")
                .text("text")
                .date(LocalDateTime.now(clock))
                .comments(List.of(commentDto))
                .build();

        assertEquals(expectedNewsDto, actualNewsDto);
    }

    @Test
    void getNewsByFullTextSearchSuccessfullyTest() {
        //given
        String searchText = "search text";
        CommentDto commentDto = CommentDto.builder()
                .username("user")
                .text("test")
                .date(LocalDateTime.now(clock))
                .newsId(1L)
                .build();

        CommentEntity commentEntity = CommentEntity.builder()
                .id(1L)
                .username("user")
                .text("test")
                .date(LocalDateTime.now(clock))
                .build();

        NewsEntity newsEntity = NewsEntity.builder()
                .id(1L)
                .title("title")
                .text("search text")
                .date(LocalDateTime.now(clock))
                .comments(List.of(commentEntity))
                .build();

        commentEntity.setNews(newsEntity);


        when(newsRepository.search(searchText))
                .thenReturn(List.of(newsEntity));

        //when
        List<NewsDto> actualNewsDto = newsService.getNewsByFullTextSearch(searchText);

        //then
        List<NewsDto> expectedNewsDto = List.of(NewsDto.builder()
                .title("title")
                .text("search text")
                .date(LocalDateTime.now(clock))
                .comments(List.of(commentDto))
                .build());

        assertEquals(expectedNewsDto, actualNewsDto);
    }
}

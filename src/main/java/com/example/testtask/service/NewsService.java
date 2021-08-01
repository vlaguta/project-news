package com.example.testtask.service;

import com.example.testtask.controller.dto.NewsCreateRequest;
import com.example.testtask.controller.dto.NewsDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface NewsService {

    /**
     * Method for  saving the entity to the database
     * @param newsCreateRequest
     */
    void create(NewsCreateRequest newsCreateRequest);


    /**
     * Method for deleting entity by id
     * @param id the entity id
     */
    void delete(long id);

    /**
     * Methods for update entity
     * @param id the entity id
     * @param newsDto the data transfer object
     */
    void update(long id, NewsDto newsDto);

    /**
     * @param pageable the object for page-by-page view
     * @return List with data transfer objects
     */
    List<NewsDto> getAll(Pageable pageable);

    /**
     * @param pageable the object for page-by-page view
     * @param id the entity id
     * @return data transfer objects
     */
    NewsDto getNewsWithPageableComments(long id, Pageable pageable);

    /**
     * @param text search parameter
     * @return List with data transfer objects
     */
    List<NewsDto> getNewsByFullTextSearch(String text);
}

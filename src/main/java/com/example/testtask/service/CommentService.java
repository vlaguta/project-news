package com.example.testtask.service;


import com.example.testtask.controller.dto.CommentCreateRequest;
import com.example.testtask.controller.dto.CommentDto;
import com.example.testtask.repository.entity.NewsEntity;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CommentService {

    /**
     * Method for  saving the entity to the database
     * @param commentCreateRequest the object to create entity
     */
    void create(CommentCreateRequest commentCreateRequest);

    /**
     * Method for deleting entity by id
     * @param id the entity id
     */
    void delete(long id);

    /**
     * Methods for update entity
     * @param id the entity id
     * @param commentDto the data transfer object
     */
    void update(long id, CommentDto commentDto);

    /**
     * @param pageable the object for page-by-page view
     * @param news the entity
     * @return List with data transfer objects
     */
    List<CommentDto> getAllWithPagination(Pageable pageable, NewsEntity news);

}

package com.example.testtask.repository;

import com.example.testtask.repository.entity.CommentEntity;
import com.example.testtask.repository.entity.NewsEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

    Page<CommentEntity> findAllByNews(Pageable pageable, NewsEntity news);
}

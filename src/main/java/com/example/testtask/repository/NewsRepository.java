package com.example.testtask.repository;

import com.example.testtask.repository.entity.NewsEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NewsRepository extends JpaRepository<NewsEntity, Long> {

    @Override
    Page<NewsEntity> findAll(Pageable pageable);

    @Override
    Optional<NewsEntity> findById(Long aLong);

    @Query("SELECT p FROM NewsEntity p WHERE fts(:text) = true")
    List<NewsEntity> search(@Param("text") String text);
}

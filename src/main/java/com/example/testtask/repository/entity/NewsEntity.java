package com.example.testtask.repository.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "news")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewsEntity {


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "news")
    @ToString.Exclude
    @Builder.Default
    List<CommentEntity> comments = new ArrayList<>();
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String text;
    private LocalDateTime date;

}

package com.practice.blog.domain.entity;

import com.practice.blog.domain.dto.PostRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;

import java.util.Objects;

@Entity
@Getter
@Table
@NoArgsConstructor
public class Post extends TimeStamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column
    private String summary;

    @ManyToOne(fetch = FetchType.LAZY)
    private User writer;

    public Post(PostRequestDto postRequestDto, User writer){
        this.content = postRequestDto.getContent();
        this.title = postRequestDto.getTitle();
        this.summary = postRequestDto.getSummary();
        this.writer = writer;
    }

    public void update(PostRequestDto postRequestDto){
        this.content = postRequestDto.getContent();
        this.title = postRequestDto.getTitle();
        this.summary = postRequestDto.getSummary();
    }

    @Override
    public boolean equals(Object o){
        if (this == o){
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)){
            return false;
        }
        Post that = (Post) o;
        return id != null && Objects.equals(id, that.getId());
    }
}

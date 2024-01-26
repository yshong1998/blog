package com.practice.blog.domain.dto;

import com.practice.blog.domain.entity.Post;
import com.practice.blog.domain.entity.User;
import lombok.Getter;

@Getter
public class PostResponseDto {

    private final Long id;

    private final String title;

    private final String content;

    private final String summary;

    private final String writer;

    public PostResponseDto(Post post){
        this.id = post.getId();
        this.title = post.getTitle();
        this.summary = post.getSummary();
        this.content = post.getContent();
        this.writer = post.getWriter().getNickname();
    }
}

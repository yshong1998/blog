package com.practice.blog.domain.dto;

import com.practice.blog.domain.entity.Comment;
import com.practice.blog.domain.entity.User;
import lombok.Getter;

@Getter
public class CommentResponseDto {

    private final Long id;
    private final String content;
    private final String writer;

    public CommentResponseDto(Comment comment){
        this.id = comment.getId();
        this.content = comment.getContent();
        this.writer = comment.getWriter().getNickname();
    }
}

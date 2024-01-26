package com.practice.blog.domain.dto;

import com.practice.blog.domain.entity.Comment;
import com.practice.blog.domain.entity.Post;
import com.practice.blog.domain.entity.User;
import lombok.Getter;

import java.util.List;

@Getter
public class PostDetailResponseDto {

    private final Long id;

    private final String title;

    private final String content;

    private final String summary;

    private final String writer;

    private final List<CommentResponseDto> commentResponseDtos;

    public PostDetailResponseDto(Post post, List<CommentResponseDto> commentResponseDtos){
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.summary = post.getSummary();
        this.writer = post.getWriter().getNickname();
        this.commentResponseDtos = commentResponseDtos;
    }
}

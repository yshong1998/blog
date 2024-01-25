package com.practice.blog.domain.dto;

import com.practice.blog.domain.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import lombok.Getter;

@Getter
public class PostRequestDto {

    private String title;

    private String summary;

    private String content;

}

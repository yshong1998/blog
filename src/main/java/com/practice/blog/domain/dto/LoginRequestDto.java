package com.practice.blog.domain.dto;


import lombok.Getter;

@Getter
public class LoginRequestDto {
    private String email;
    private String password;
}
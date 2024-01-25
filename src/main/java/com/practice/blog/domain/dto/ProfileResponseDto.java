package com.practice.blog.domain.dto;

import com.practice.blog.domain.entity.User;
import lombok.Getter;

@Getter
public class ProfileResponseDto {

    private String email;
    private String nickname;

    public ProfileResponseDto(User user){
        this.email = user.getEmail();
        this.nickname = user.getNickname();
    }
}

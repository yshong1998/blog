package com.practice.blog.response.message;

import lombok.Getter;

@Getter
public class MessageResponseDto {

    private final String message;

    public MessageResponseDto(Message message){
        this.message = message.returnMessageAsString();
    }
}

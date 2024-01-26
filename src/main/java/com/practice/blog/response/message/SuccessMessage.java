package com.practice.blog.response.message;

import lombok.Getter;

@Getter
public enum SuccessMessage {

    // Users
    POST_SIGNUP_SUCCESS("회원 가입에 성공했습니다."),
    POST_LOGIN_SUCCESS("로그인에 성공했습니다."),
    GET_PROFILE_SUCCESS("프로필이 로드되었습니다."),

    // Posts
    POST_POSTING_SUCCESS("포스팅이 작성되었습니다."),
    PUT_POSTING_SUCCESS("포스팅이 수정되었습니다."),

    // Comment
    POST_COMMENT_SUCCESS("댓글이 작성되었습니다."),
    PUT_COMMENT_SUCCESS("댓글이 수정되었습니다.");

    private final String message;

    SuccessMessage(String message){
        this.message = message;
    }
}

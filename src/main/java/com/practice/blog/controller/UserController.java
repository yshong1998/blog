package com.practice.blog.controller;

import com.practice.blog.domain.dto.LoginRequestDto;
import com.practice.blog.domain.dto.ProfileResponseDto;
import com.practice.blog.domain.dto.SignupRequestDto;
import com.practice.blog.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    // 회원 가입
    @PostMapping("/signup")
    public void signup(@RequestBody SignupRequestDto signupRequestDto) {
        userService.signup(signupRequestDto);
    }

    @PostMapping("/login")
    public void login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response){
        userService.login(loginRequestDto, response);
    }

    // 본인 프로필 조회
    @GetMapping("/profile")
    public ProfileResponseDto readUserProfile(HttpServletRequest request){
        return userService.readUserProfile(request);
    }
}

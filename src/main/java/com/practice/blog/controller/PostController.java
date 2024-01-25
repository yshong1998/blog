package com.practice.blog.controller;

import com.practice.blog.domain.dto.PostDetailResponseDto;
import com.practice.blog.domain.dto.PostRequestDto;
import com.practice.blog.domain.dto.PostResponseDto;
import com.practice.blog.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    @PostMapping
    public void createPost(@RequestBody PostRequestDto postRequestDto,
                           HttpServletRequest request) {
        postService.createPost(postRequestDto, request);
    }

    @PutMapping("{postId}")
    public void updatePost(@PathVariable Long postId,
                           @RequestBody PostRequestDto postRequestDto,
                           HttpServletRequest request) {
        postService.updatePost(postId, postRequestDto, request);
    }

    @GetMapping
    public Page<PostResponseDto> readPosts(@PageableDefault(size = 5, sort = {"createdAt"}, direction = Sort.Direction.DESC) Pageable pageable) {
        return postService.readPosts(pageable);
    }

    @GetMapping("/{postId}")
    public PostDetailResponseDto readPost(@PathVariable Long postId){
        return postService.readPost(postId);
    }
}

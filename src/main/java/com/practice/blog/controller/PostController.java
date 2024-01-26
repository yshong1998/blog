package com.practice.blog.controller;

import com.practice.blog.domain.dto.PostDetailResponseDto;
import com.practice.blog.domain.dto.PostRequestDto;
import com.practice.blog.domain.dto.PostResponseDto;
import com.practice.blog.response.message.SuccessMessage;
import com.practice.blog.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<SuccessMessage> createPost(@RequestBody PostRequestDto postRequestDto,
                                                     HttpServletRequest request) {
        return postService.createPost(postRequestDto, request);
    }

    @PutMapping("{postId}")
    public ResponseEntity<SuccessMessage> updatePost(@PathVariable Long postId,
                           @RequestBody PostRequestDto postRequestDto,
                           HttpServletRequest request) {
        return postService.updatePost(postId, postRequestDto, request);
    }

    @GetMapping
    public ResponseEntity<Page<PostResponseDto>> readPosts(@PageableDefault(size = 5, sort = {"createdAt"}, direction = Sort.Direction.DESC) Pageable pageable) {
        return postService.readPosts(pageable);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostDetailResponseDto> readPost(@PathVariable Long postId){
        return postService.readPost(postId);
    }
}

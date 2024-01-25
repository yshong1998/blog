package com.practice.blog.controller;

import com.practice.blog.domain.dto.CommentRequestDto;
import com.practice.blog.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{postId}")
    public void createComment(@RequestBody CommentRequestDto commentRequestDto,
                              @PathVariable Long postId,
                              HttpServletRequest request){
        commentService.createComment(commentRequestDto, postId, request);
    }

    @PutMapping("{postId}/{commentId}")
    public void updateComment(@RequestBody CommentRequestDto commentRequestDto,
                              @PathVariable Long postId,
                              @PathVariable Long commentId,
                              HttpServletRequest request){
        commentService.updateComment(commentRequestDto, postId, commentId, request);
    }
}

package com.practice.blog.controller;

import com.practice.blog.domain.dto.CommentRequestDto;
import com.practice.blog.response.message.SuccessMessage;
import com.practice.blog.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{postId}")
    public ResponseEntity<SuccessMessage> createComment(@RequestBody CommentRequestDto commentRequestDto,
                                                        @PathVariable Long postId,
                                                        HttpServletRequest request){
        return commentService.createComment(commentRequestDto, postId, request);
    }

    @PutMapping("{postId}/{commentId}")
    public ResponseEntity<SuccessMessage> updateComment(@RequestBody CommentRequestDto commentRequestDto,
                              @PathVariable Long postId,
                              @PathVariable Long commentId,
                              HttpServletRequest request){
        return commentService.updateComment(commentRequestDto, postId, commentId, request);
    }
}

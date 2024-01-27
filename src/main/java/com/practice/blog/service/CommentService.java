package com.practice.blog.service;

import com.practice.blog.domain.dto.CommentRequestDto;
import com.practice.blog.domain.entity.Comment;
import com.practice.blog.domain.entity.Post;
import com.practice.blog.domain.entity.User;
import com.practice.blog.repository.CommentRepository;
import com.practice.blog.repository.PostRepository;
import com.practice.blog.response.message.MessageResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.practice.blog.response.message.SuccessMessage.POST_COMMENT_SUCCESS;
import static com.practice.blog.response.message.SuccessMessage.PUT_COMMENT_SUCCESS;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public ResponseEntity<MessageResponseDto> createComment(CommentRequestDto commentRequestDto, Long postId, HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 포스트입니다.")
        );
        commentRepository.save(new Comment(user, post, commentRequestDto));
        return ResponseEntity.ok(new MessageResponseDto(POST_COMMENT_SUCCESS));
    }

    @Transactional
    public ResponseEntity<MessageResponseDto> updateComment(CommentRequestDto commentRequestDto, Long postId, Long commentId, HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 포스트입니다.")
        );
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 댓글입니다.")
        );
        if (!comment.getWriter().equals(user)) {
            throw new IllegalArgumentException("권한이 없습니다..");
        }
        comment.update(commentRequestDto);
        return ResponseEntity.ok(new MessageResponseDto(PUT_COMMENT_SUCCESS));
    }
}

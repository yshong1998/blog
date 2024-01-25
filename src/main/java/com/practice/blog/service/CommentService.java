package com.practice.blog.service;

import com.practice.blog.domain.dto.CommentRequestDto;
import com.practice.blog.domain.dto.CommentResponseDto;
import com.practice.blog.domain.entity.Comment;
import com.practice.blog.domain.entity.Post;
import com.practice.blog.domain.entity.User;
import com.practice.blog.repository.CommentRepository;
import com.practice.blog.repository.PostRepository;
import com.practice.blog.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public void createComment(CommentRequestDto commentRequestDto, Long postId, HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 포스트입니다.")
        );
        commentRepository.save(new Comment(user, post, commentRequestDto));
    }

    @Transactional
    public void updateComment(CommentRequestDto commentRequestDto, Long postId, Long commentId, HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 포스트입니다.")
        );
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 댓글입니다.")
        );
        if (!comment.getWriter().equals(user)){
            throw new IllegalArgumentException("권한이 없습니다..");
        }
        comment.update(commentRequestDto);
    }
}

package com.practice.blog.service;

import com.practice.blog.domain.dto.CommentResponseDto;
import com.practice.blog.domain.dto.PostDetailResponseDto;
import com.practice.blog.domain.dto.PostRequestDto;
import com.practice.blog.domain.dto.PostResponseDto;
import com.practice.blog.domain.entity.Comment;
import com.practice.blog.domain.entity.Post;
import com.practice.blog.domain.entity.User;
import com.practice.blog.repository.CommentRepository;
import com.practice.blog.repository.PostRepository;
import com.practice.blog.response.message.SuccessMessage;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public ResponseEntity<SuccessMessage> createPost(PostRequestDto postRequestDto, HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        postRepository.save(new Post(postRequestDto, user));
        return ResponseEntity.ok(SuccessMessage.POST_POSTING_SUCCESS);
    }

    @Transactional
    public ResponseEntity<SuccessMessage> updatePost(Long postId, PostRequestDto postRequestDto, HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 포스트입니다.")
        );
        if (!post.getWriter().equals(user)) {
            throw new IllegalArgumentException("권한이 없습니다.");
        }
        post.update(postRequestDto);
        return ResponseEntity.ok(SuccessMessage.PUT_POSTING_SUCCESS);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<Page<PostResponseDto>> readPosts(Pageable pageable) {
        Page<Post> postPages = postRepository.findAllByOrderByCreatedAtDesc(pageable);
        return ResponseEntity.ok(postPages.map(PostResponseDto::new));
    }

    public ResponseEntity<PostDetailResponseDto> readPost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 게시글입니다.")
        );
        List<Comment> comments = commentRepository.findCommentsByPost(post);
        List<CommentResponseDto> commentResponseDtos = comments
                .stream()
                .map(CommentResponseDto::new)
                .toList();
        return ResponseEntity.ok(new PostDetailResponseDto(post, commentResponseDtos));
    }
}

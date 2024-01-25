package com.practice.blog.repository;

import com.practice.blog.domain.entity.Comment;
import com.practice.blog.domain.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>{

    List<Comment> findCommentsByPost(Post post);
}

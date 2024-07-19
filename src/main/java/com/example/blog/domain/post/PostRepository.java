package com.example.blog.domain.post;

import com.example.blog.domain.blog.Blog;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllByAuthorBlog(Blog blog);

}

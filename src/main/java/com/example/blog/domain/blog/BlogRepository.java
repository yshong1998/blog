package com.example.blog.domain.blog;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepository extends JpaRepository<Blog, Long> {

    Blog findByUserId(Long userId);
    Blog findByBlogTitle(String blogTitle);
}

package com.example.blog.domain.blog;

import com.example.blog.domain.post.Post;
import com.example.blog.domain.post.PostRepository;
import com.example.blog.domain.user.User;
import com.example.blog.web.blog.BlogResponseDto;
import com.example.blog.web.post.PostResponseDto;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BlogService {
    private final BlogRepository blogRepository;
    private final PostRepository postRepository;

    public BlogResponseDto getBlog(User user, String blogTitle) {
        Blog blog = blogRepository.findByBlogTitle(blogTitle);
        List<Post> blogPosts = postRepository.findAllByAuthorBlog(blog);
        List<PostResponseDto> blogPostsDto = blogPosts.stream().map(PostResponseDto::new).collect(Collectors.toList());
        return new BlogResponseDto(blog, blogPostsDto);
    }
}

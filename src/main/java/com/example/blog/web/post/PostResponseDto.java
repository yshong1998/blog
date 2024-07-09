package com.example.blog.web.post;

import com.example.blog.domain.blog.Blog;
import com.example.blog.domain.post.Post;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostResponseDto {

    private Long id;
    private String title;
    private List<String> tags;
    private String contents;
    private LocalDateTime createdAt;
    private Integer viewCount;
    private String thumbnail;
    private String author;
    private String authorBlogTitle;

    public PostResponseDto(Post post){
        this.id = post.getId();
        this.title = post.getTitle();
        this.contents = post.getContents();
        this.createdAt = post.getCreatedAt();
        this.viewCount = post.getViewCount();
        this.thumbnail = post.getThumbnailUrl();
        this.author = post.getAuthorBlog().getUserName();
        this.authorBlogTitle = post.getAuthorBlog().getBlogTitle();
    }
}

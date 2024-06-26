package com.example.blog.web.post;

import com.example.blog.domain.post.Post;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostResponseDto {

    private Long id;
    private String title;
    private String contents;
    private LocalDateTime createdAt;
    private Integer viewCount;
    private String thumbnail;

    public PostResponseDto(Post post){
        this.id = post.getId();
        this.title = post.getTitle();
        this.contents = post.getContents();
        this.createdAt = post.getCreatedAt();
        this.viewCount = post.getViewCount();
        this.thumbnail = post.getThumbnail();
    }
}

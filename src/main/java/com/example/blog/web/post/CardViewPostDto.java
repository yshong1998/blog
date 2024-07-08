package com.example.blog.web.post;

import com.example.blog.domain.post.Post;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class CardViewPostDto {
    private Long id;
    private String title;
    private String contentsSummary;
    private LocalDateTime createdAt;
    private Integer viewCount;
    private String thumbnail;
    private String authorName;
    private String authorProfileImageUrl;

    public CardViewPostDto(Post post){
        this.id = post.getId();
        this.title = post.getTitle();
        this.contentsSummary = post.getContentsSummary();
        this.createdAt = post.getCreatedAt();
        this.viewCount = post.getViewCount();
        this.thumbnail = post.getThumbnailUrl();
        this.authorName = post.getAuthor().getUserName();
        this.authorProfileImageUrl = post.getAuthor().getProfileImageUrl();
    }
}

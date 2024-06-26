package com.example.blog.domain.post;

import com.example.blog.web.post.PostRequestDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Post {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String contents;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private Integer viewCount;
    private Boolean isTemp;
    private String thumbnail;

    public Post(PostRequestDto postRequestDto){
        this.title = postRequestDto.getTitle();
        this.contents = postRequestDto.getContents();
        this.isTemp = postRequestDto.getIsTemp();
        this.thumbnail = postRequestDto.getThumbnail();
        this.createdAt = LocalDateTime.now();
        this.viewCount = 0;
    }
}

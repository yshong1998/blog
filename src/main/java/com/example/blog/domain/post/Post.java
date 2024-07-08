package com.example.blog.domain.post;

import com.example.blog.domain.s3.S3Const;
import com.example.blog.domain.series.Series;
import com.example.blog.domain.user.User;
import com.example.blog.web.post.PostRequestDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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
    @Column(columnDefinition = "TEXT")
    private String contents;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private Integer viewCount;
    private Boolean isTemp;
    private String thumbnailUrl;
    @ManyToOne
    private User author;
    @ManyToOne
    private Series series;

    public Post(PostRequestDto postRequestDto, User author, Series series){
        this.title = postRequestDto.getTitle();
        this.contents = postRequestDto.getContents();
        this.isTemp = postRequestDto.getIsTemp();
        this.thumbnailUrl = S3Const.DEFAULT_POST_THUMBNAIL_FILE_LOCATION;
        this.createdAt = LocalDateTime.now();
        this.viewCount = 0;
        this.author = author;
        this.series = series;
    }

    public void setThumbnailUrl(String thumbnailUrl){
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getContentsSummary(){
        if (contents.length() > 150){
            return contents.substring(0,150) + "...";
        }
        return contents;
    }
}

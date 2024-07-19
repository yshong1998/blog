package com.example.blog.web.blog;

import com.example.blog.domain.blog.Blog;
import com.example.blog.web.post.PostResponseDto;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BlogResponseDto {

    private String blogTitle;
    private String blogOwner;
    private String blogOwnerProfileImageUrl;
    private String blogOwnerIntroduce;
    private List<PostResponseDto> postResponseDtos;

    public BlogResponseDto(Blog blog, List<PostResponseDto> postResponseDtos){
        this.blogTitle = blog.getBlogTitle();
        this.blogOwner = blog.getUserName();
        this.blogOwnerProfileImageUrl = blog.getProfileImageUrl();
        this.blogOwnerIntroduce = blog.getIntroduce();
        this.postResponseDtos = postResponseDtos;
    }

}

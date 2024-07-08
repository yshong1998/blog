package com.example.blog.web.post;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostRequestDto {

    private String title;
    private String tag;
    private String contents;
    private Boolean isTemp;
    private MultipartFile thumbnail;
    private String summary;
    private String seriesTitle;


}

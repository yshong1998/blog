package com.example.blog.web.series;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SeriesResponseDto {

    private String seriesTitle;

    public SeriesResponseDto(String seriesTitle){
        this.seriesTitle = seriesTitle;
    }

}

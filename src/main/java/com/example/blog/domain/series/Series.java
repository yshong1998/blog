package com.example.blog.domain.series;

import com.example.blog.domain.blog.Blog;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Series {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String seriesTitle;
    @ManyToOne
    private Blog blog;

    public Series(Blog blog, String seriesTitle){
        this.blog = blog;
        this.seriesTitle = seriesTitle;
    }

}

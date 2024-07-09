package com.example.blog.domain.series;

import com.example.blog.domain.blog.Blog;
import com.example.blog.domain.blog.BlogRepository;
import com.example.blog.domain.user.User;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SeriesService {
    private final SeriesRepository seriesRepository;
    private final BlogRepository blogRepository;

    @Transactional
    public String createSeries(User user, String seriesTitle){
        Blog userBlog = blogRepository.findByUserId(user.getId());
        Series newSeries = seriesRepository.save(new Series(userBlog, seriesTitle));
        return newSeries.getSeriesTitle();
    }

    @Transactional
    public List<String> getUserSeries(User user){
        Blog userBlog = blogRepository.findByUserId(user.getId());
        List<Series> blogSeries = seriesRepository.findAllByBlogId(userBlog.getId());
        return blogSeries.stream().map(Series::getSeriesTitle).collect(Collectors.toList());
    }
}

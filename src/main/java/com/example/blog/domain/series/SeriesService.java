package com.example.blog.domain.series;

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

    @Transactional
    public List<String> getUserSeries(User user){
        List<Series> userSeries = seriesRepository.findAllByUserId(user.getId());
        return userSeries.stream().map(Series::getSeriesTitle).collect(Collectors.toList());
    }


}

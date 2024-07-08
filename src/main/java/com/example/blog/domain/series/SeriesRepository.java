package com.example.blog.domain.series;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeriesRepository extends JpaRepository<Series, Long> {
    List<Series> findAllByUserId(Long userId);
    Optional<Series> findByUserIdAndSeriesTitle(Long userId, String seriesTitle);
}

package com.example.blog.web.series;

import com.example.blog.domain.series.SeriesService;
import com.example.blog.domain.user.User;
import com.example.blog.global.security.SecurityConst;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
@RequiredArgsConstructor
public class SeriesController {

    private final SeriesService seriesService;

    @ResponseBody
    @GetMapping("/series")
    public List<String> getUserSeries(@SessionAttribute(name = SecurityConst.BLOG_SID)User user){
        return seriesService.getUserSeries(user);
    }
}

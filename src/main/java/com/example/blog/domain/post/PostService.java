package com.example.blog.domain.post;

import com.example.blog.domain.blog.Blog;
import com.example.blog.domain.blog.BlogRepository;
import com.example.blog.domain.s3.S3Const;
import com.example.blog.domain.s3.S3Uploader;
import com.example.blog.domain.series.Series;
import com.example.blog.domain.series.SeriesRepository;
import com.example.blog.domain.user.User;
import com.example.blog.web.post.CardViewPostDto;
import com.example.blog.web.post.PostRequestDto;
import com.example.blog.web.post.PostResponseDto;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final SeriesRepository seriesRepository;
    private final BlogRepository blogRepository;
    private final S3Uploader s3Uploader;

    @Transactional
    public PostResponseDto createPost(PostRequestDto postRequestDto, User user) {
        Blog userBlog = blogRepository.findByUserId(user.getId());
        Optional<Series> selectedSeries = seriesRepository
                .findByBlogIdAndSeriesTitle(userBlog.getId(), postRequestDto.getSeriesTitle());
        Post post = selectedSeries.isEmpty() ?
                new Post(postRequestDto, userBlog, null) : new Post(postRequestDto, userBlog, selectedSeries.get());
        if (!postRequestDto.getThumbnail().isEmpty()) {
            String thumbnailUrl = s3Uploader.saveFile(postRequestDto.getThumbnail(),
                    S3Const.POST_THUMBNAIL_UPLOAD_DIRECTORY);
            post.setThumbnailUrl(thumbnailUrl);
        }
        postRepository.save(post);
        log.info("게시글 저장 완료");
        return new PostResponseDto(post);
    }

    public List<CardViewPostDto> getPosts() {
        List<Post> posts = postRepository.findAll();
        List<CardViewPostDto> cardViewPostDtos = new ArrayList<>();
        for (int i = 0; i < posts.size(); i++) {
            cardViewPostDtos.add(new CardViewPostDto(posts.get(i)));
        }
        return cardViewPostDtos;
    }


    public PostResponseDto getPost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 게시글입니다.")
        );
        return new PostResponseDto(post);
    }
}

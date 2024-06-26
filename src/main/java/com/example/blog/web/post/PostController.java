package com.example.blog.web.post;

import com.example.blog.domain.post.PostService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/api/post")
    public String createPost(@RequestBody PostRequestDto requestDto){
        postService.createPost(requestDto);
        return "redirect:/api/post";
    }

    @GetMapping("/api/post")
    public String getPosts(Model model){
        List<PostResponseDto> posts = postService.getPosts();
        System.out.println(posts.get(0).getThumbnail());
        model.addAttribute("posts", posts);
        return "index";
    }

    @GetMapping("/api/post/{post_id}")
    public String getPost(@PathVariable Long post_id, Model model){
        PostResponseDto post = postService.getPost(post_id);
        model.addAttribute("post", post);
        return "postDetailPage";
    }
}

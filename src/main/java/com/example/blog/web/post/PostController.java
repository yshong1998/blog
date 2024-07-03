package com.example.blog.web.post;

import com.example.blog.domain.post.PostService;
import com.example.blog.domain.user.User;
import com.example.blog.global.security.SecurityConst;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/post")
    public String createPost(@RequestBody PostRequestDto requestDto){
        postService.createPost(requestDto);
        return "redirect:/post";
    }

    @GetMapping("/post")
    public String getPosts(@SessionAttribute(name = SecurityConst.BLOG_SID, required = false)User user, Model model){
        List<PostResponseDto> posts = postService.getPosts();
        model.addAttribute("posts", posts);
        if (user != null){
            model.addAttribute("user", user);
            return "/loginHome";
        }
        return "home";
    }

    @GetMapping("/post/{post_id}")
    public String getPost(@PathVariable Long post_id, Model model){
        PostResponseDto post = postService.getPost(post_id);
        model.addAttribute("post", post);
        return "postDetailPage";
    }
}

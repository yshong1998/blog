package com.example.blog.web.blog;

import com.example.blog.domain.blog.BlogService;
import com.example.blog.domain.user.User;
import com.example.blog.global.security.SecurityConst;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
@RequiredArgsConstructor
public class BlogController {

    private final BlogService blogService;

    @GetMapping("/{blog_title}/posts")
    public String getBlog(@SessionAttribute(name = SecurityConst.BLOG_SID, required = false) User user, @PathVariable String blog_title, Model model){
        BlogResponseDto blogInfo = blogService.getBlog(user, blog_title);
        model.addAttribute("blogInfo", blogInfo);
        return "blogPage";
    }
}

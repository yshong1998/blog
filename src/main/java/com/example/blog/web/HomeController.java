package com.example.blog.web;

import com.example.blog.domain.user.User;
import com.example.blog.global.security.SecurityConst;
import com.example.blog.web.post.PostRequestDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(){
        return "redirect:/post";
    }

    @GetMapping("/write")
    public String getPostWriteView(
            @SessionAttribute(name = SecurityConst.BLOG_SID) User user, Model model){
        model.addAttribute("postRequestDto", new PostRequestDto());
        return "writePostPage";
    }
}

package com.example.blog.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(){
        return "redirect:/post";
    }

    @GetMapping("/write")
    public String getPostView(){
        return "writePostPage";
    }

}

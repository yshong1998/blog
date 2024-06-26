package com.example.blog.web;

import com.example.blog.web.login.LoginForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {

    @GetMapping("/home")
    public String home(){
        return "redirect:/api/post";
    }

    @GetMapping("/login-page")
    public String loginPage(Model model){
        model.addAttribute("loginForm", new LoginForm());
        return "loginPage";
    }

    @GetMapping("/success-page")
    public String success(){
        return "success";
    }

}

package com.example.blog.web.user;

import com.example.blog.domain.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userservice;

    @GetMapping("/signup")
    public String getSignupForm(@ModelAttribute("signupForm") SignupForm form){
        return "loginPage";
    }

    @PostMapping("/signup")
    public String signup(@Valid @ModelAttribute SignupForm form, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "users/signupForm";
        }
        userservice.signup(form);
        return "redirect:/";
    }
}

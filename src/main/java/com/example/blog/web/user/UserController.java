package com.example.blog.web.user;

import com.example.blog.domain.user.UserService;
import jakarta.validation.Valid;
import java.sql.SQLIntegrityConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserService userservice;

    @GetMapping("/signup")
    public String getSignupForm(@ModelAttribute("signupForm") SignupForm form){
        return "signupPage";
    }

    @PostMapping("/signup")
    public String signup(@Valid @ModelAttribute SignupForm form, BindingResult bindingResult)
            throws SQLIntegrityConstraintViolationException {
        if (bindingResult.hasErrors()){
            return "signupPage";
        }
        String redirectionUrl = userservice.signup(form, bindingResult);
        return "redirect:" + redirectionUrl;
    }
}

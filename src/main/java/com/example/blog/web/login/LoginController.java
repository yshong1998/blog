package com.example.blog.web.login;

import com.example.blog.domain.login.LoginService;
import com.example.blog.domain.user.User;
import com.example.blog.global.security.SecurityConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @GetMapping("/login")
    public String getLoginForm(@ModelAttribute LoginForm loginForm){
        return "/loginPage";
    }

    @PostMapping("/login")
    public String login(
            @Valid @ModelAttribute LoginForm loginForm, BindingResult bindingResult,
                        @RequestParam(defaultValue = "/") String redirectUrl,
                        HttpServletRequest request){
        // 아이디 비밀번호가 틀린 경우가 아닌 그 외의 에러가 있을 경우
        if (bindingResult.hasErrors()){
            return "/loginPage";
        }

        // 로그인 시도
        User loginUser = loginService.login(loginForm.getLoginId(), loginForm.getLoginPassword());

        // 아이디 비밀번호가 일치하는 유저가 없는 경우
        if (loginUser == null){
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 일치하지 않습니다.");
            return "/loginPage";
        }

        // 도달 시 로그인 성공.
        HttpSession session = request.getSession();
        session.setAttribute(SecurityConst.BLOG_SID, loginUser);
        return "redirect:" + redirectUrl;
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request){
        HttpSession session = request.getSession();
        if (session != null){
            session.invalidate();
        }
        return "redirect:/";
    }

}

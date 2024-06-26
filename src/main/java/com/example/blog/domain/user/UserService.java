package com.example.blog.domain.user;

import static com.example.blog.domain.user.Role.*;

import com.example.blog.web.user.SignupForm;
import java.sql.SQLIntegrityConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final String ADMIN_CODE = "1234";

    @Transactional
    public String signup(SignupForm form, BindingResult bindingResult) throws SQLIntegrityConstraintViolationException {
        Role userRole = USER;
        if (!form.getAdminCode().isEmpty()){
            if (!ADMIN_CODE.equals(form.getAdminCode())){
                bindingResult.reject("signupFail", "관리자 코드가 일치하지 않습니다.");
                return "signupPage";
            } else {
                userRole = ADMIN;
            }
        }
        User signupUser = new User(form, userRole);
        userRepository.save(signupUser);
        return "home";
    }
}

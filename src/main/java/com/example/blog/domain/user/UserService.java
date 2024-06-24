package com.example.blog.domain.user;

import static com.example.blog.domain.user.Role.*;

import com.example.blog.web.user.SignupForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final String ADMIN_CODE = "1234";

    @Transactional
    public void signup(SignupForm form){
        User signupUser = ADMIN_CODE.equals(form.getAdminCode()) ?
                new User(form, ADMIN) : new User(form, USER);
        userRepository.save(signupUser);
    }
}

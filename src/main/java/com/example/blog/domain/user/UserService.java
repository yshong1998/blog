package com.example.blog.domain.user;

import static com.example.blog.domain.user.Role.*;

import com.example.blog.domain.blog.Blog;
import com.example.blog.domain.blog.BlogRepository;
import com.example.blog.domain.s3.S3Const;
import com.example.blog.domain.s3.S3Uploader;
import com.example.blog.web.user.SignupForm;
import java.sql.SQLIntegrityConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final S3Uploader s3Uploader;
    private final BlogRepository blogRepository;
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
        if (!form.getProfileImage().isEmpty()){
            String uploadLocation = s3Uploader.saveFile(form.getProfileImage(), S3Const.USER_PROFILE_IMAGE_UPLOAD_DIRECTORY);
            signupUser.setProfileImageUrl(uploadLocation);
        }
        User user = userRepository.save(signupUser);
        blogRepository.save(new Blog(user, form.getBlogTitle()));
        return "/";
    }
}

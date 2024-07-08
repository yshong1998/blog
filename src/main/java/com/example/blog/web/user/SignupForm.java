package com.example.blog.web.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
public class SignupForm {
    private String username;
    @NotEmpty
    private String loginId;
    @NotEmpty
    private String loginPassword;
    @NotEmpty
    @Email
    private String email;
    private MultipartFile profileImage;
    private String introduce;
    private String adminCode;
}

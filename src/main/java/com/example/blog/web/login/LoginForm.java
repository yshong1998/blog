package com.example.blog.web.login;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LoginForm {

    @NotEmpty
    private String loginId;
    @NotEmpty
    private String loginPassword;

}

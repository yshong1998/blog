package com.example.blog.domain.user;

import com.example.blog.web.user.SignupForm;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "users",
        uniqueConstraints =
        {@UniqueConstraint(
                name = "중복 존재",
                columnNames = {"loginId", "email"})})
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String loginId;
    private String loginPassword;
    private String email;
    @Enumerated(EnumType.STRING)
    private Role role;
    private String profileImageUrl;
    private String introduce;

    public User(SignupForm form, Role role){
        this.loginId = form.getLoginId();
        this.loginPassword = form.getLoginPassword();
        this.email = form.getEmail();
        this.role = role;
        this.profileImageUrl = "tempUrl";
        this.introduce = form.getIntroduce();
    }

}

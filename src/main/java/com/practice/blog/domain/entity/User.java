package com.practice.blog.domain.entity;

import com.practice.blog.domain.dto.SignupRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;

import java.util.Objects;

@Entity
@Getter
@Table(name = "users")
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true, nullable = false)
    private String nickname;

    @Column(unique = true, nullable = false)
    private String password;

    public User(SignupRequestDto signupRequestDto){
        this.email = signupRequestDto.getEmail();
        this.nickname = signupRequestDto.getNickname();
        this.password = signupRequestDto.getPassword();
    }

    @Override
    public boolean equals(Object o){
        if (this == o){
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)){
            return false;
        }
        User that = (User) o;
        return id != null && Objects.equals(id, that.getId());
    }
}

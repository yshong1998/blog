package com.example.blog.domain.blog;

import com.example.blog.domain.user.Role;
import com.example.blog.domain.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Blog {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String blogTitle;
    @Enumerated(EnumType.STRING)
    private Role role;
    private String profileImageUrl;
    private String introduce;
    private String userName;
    @OneToOne
    private User user;

    public Blog(User user, String blogTitle){
        this.user = user;
        this.blogTitle = blogTitle;
        this.userName = user.getUserName();
        this.introduce = user.getIntroduce();
        this.profileImageUrl = user.getProfileImageUrl();
        this.role = user.getRole();
    }
}

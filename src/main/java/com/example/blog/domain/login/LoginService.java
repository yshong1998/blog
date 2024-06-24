package com.example.blog.domain.login;


import com.example.blog.domain.user.User;
import com.example.blog.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final UserRepository userRepository;

    public User login(String loginId, String loginPassword) {
        return userRepository.findByLoginId(loginId)
                .filter(user -> user.getLoginPassword().equals(loginPassword))
                .orElse(null);
    }
}

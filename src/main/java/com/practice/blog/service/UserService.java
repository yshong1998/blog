package com.practice.blog.service;

import com.practice.blog.domain.dto.LoginRequestDto;
import com.practice.blog.domain.dto.ProfileResponseDto;
import com.practice.blog.domain.dto.SignupRequestDto;
import com.practice.blog.domain.entity.User;
import com.practice.blog.jwt.JwtUtil;
import com.practice.blog.repository.UserRepository;
import com.practice.blog.response.message.SuccessMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Transactional
    public ResponseEntity<SuccessMessage> signup(SignupRequestDto signupRequestDto) {
        String email = signupRequestDto.getEmail();
        Optional<User> findUser = userRepository.findByEmail(email);
        if (findUser.isPresent()){
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }
        userRepository.save(new User(signupRequestDto));
        return ResponseEntity.ok(SuccessMessage.POST_SIGNUP_SUCCESS);
    }

    @Transactional
    public ResponseEntity<SuccessMessage> login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
        String email = loginRequestDto.getEmail();
        String password = loginRequestDto.getPassword();

        // 사용자 확인
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new IllegalArgumentException("등록된 사용자가 없습니다.")
        );

        // 비밀 번호 확인
        if (!password.equals(user.getPassword())){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // JWT 생성 및 쿠키에 저장 후 Response 객체에 추가
        String token = jwtUtil.createToken(user.getNickname());
        jwtUtil.addJwtToCookie(token, response);
        return ResponseEntity.ok(SuccessMessage.POST_LOGIN_SUCCESS);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<ProfileResponseDto> readUserProfile(HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        return ResponseEntity.ok(new ProfileResponseDto(user));
    }
}

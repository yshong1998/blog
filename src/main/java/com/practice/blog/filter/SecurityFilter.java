package com.practice.blog.filter;

import com.practice.blog.domain.entity.User;
import com.practice.blog.jwt.JwtUtil;
import com.practice.blog.repository.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;

@Slf4j(topic = "UserFilter")
@Component
@Order(2)
@RequiredArgsConstructor
public class SecurityFilter implements Filter {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String url = httpServletRequest.getRequestURI();
        String method = httpServletRequest.getMethod();

        if (url.contains("signup") ||
                url.contains("login") ||
                url.contains("h2-console") ||
                (url.contains("posts") && method.equals("GET"))) {
            chain.doFilter(request, response);
        } else {
            // 나머지 API 요청은 인증 처리 진행
            // 토큰 확인
            String tokenValue = jwtUtil.getTokenFromRequest(httpServletRequest);

            if (StringUtils.hasText(tokenValue)) { // 토큰이 존재하면 검증 시작
                // JWT 토큰 substring
                String token = jwtUtil.substringToken(tokenValue);
                // 토큰 검증
                jwtUtil.validateToken(token);
                // 토큰에서 사용자 정보 가져오기
                Claims info = jwtUtil.getUserInfoFromToken(token);
                log.info(info.getSubject());
                User user = userRepository.findByNickname(info.getSubject()).orElseThrow(() ->
                        new IllegalArgumentException("존재하지 않는 사용자입니다.")
                );

                request.setAttribute("user", user);
                chain.doFilter(request, response); // 다음 Filter 로 이동
            } else {
                throw new IllegalArgumentException("토큰이 없습니다.");
            }
        }
    }
}
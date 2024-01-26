package com.practice.blog;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.practice.blog.domain.dto.LoginRequestDto;
import com.practice.blog.domain.dto.SignupRequestDto;
import com.practice.blog.jwt.JwtUtil;
import com.practice.blog.repository.UserRepository;
import com.practice.blog.response.message.SuccessMessage;
import com.practice.blog.service.UserService;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
public class UserTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void clearRepository() {
        userRepository.deleteAll();
    }

    @ParameterizedTest
    @CsvSource(value = {"yshong1998@naver.com, yshong1998, 1234"})
    @DisplayName("[user domain test] : 회원 가입 테스트 ")
    void 회원가입_테스트(String email, String nickname, String password) throws Exception {
        signup(email, nickname, password);
    }

    @ParameterizedTest
    @CsvSource(value = {"yshong1998@naver.com, yshong1998, 1234"})
    @DisplayName("[User domain test] : 로그인 테스트 ")
    void 로그인_테스트(String email, String nickname, String password) throws Exception {
        signup(email, nickname, password);
        loginAndGetCookie(email, password);
    }

    @ParameterizedTest
    @CsvSource(value = {"yshong1998@naver.com, yshong1998, 1234"})
    @DisplayName("[User domain test] : 프로필 조회 테스트")
    void 프로필_조회_테스트(String email, String nickname, String password) throws Exception {
        signup(email, nickname, password);
        Cookie cookie = loginAndGetCookie(email, password);
        getProfileByCookie(email, nickname, cookie);
    }

    private void signup(String email, String nickname, String password) throws Exception {
        SignupRequestDto signupRequestDto = new SignupRequestDto
                (email, nickname, password);

        mockMvc.perform(post("/api/user/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signupRequestDto))
                )
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(SuccessMessage.POST_SIGNUP_SUCCESS)));

    }

    private Cookie loginAndGetCookie(String email, String password) throws Exception {
        LoginRequestDto loginRequestDto = new LoginRequestDto(email, password);
        return mockMvc.perform(post("/api/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequestDto))
                )
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(SuccessMessage.POST_LOGIN_SUCCESS)))
                .andExpect(cookie().exists("Authorization"))
                .andReturn().getResponse().getCookie("Authorization");
    }

    private void getProfileByCookie(String email, String nickname, Cookie cookie) throws Exception {
        mockMvc.perform(get("/api/user/profile")
                        .contentType(MediaType.APPLICATION_JSON)
                        .cookie(cookie))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(email))
                .andExpect(jsonPath("$.nickname").value(nickname));
    }
}

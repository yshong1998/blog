package com.practice.blog;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.practice.blog.domain.dto.LoginRequestDto;
import com.practice.blog.domain.dto.SignupRequestDto;
import com.practice.blog.filter.LoggingFilter;
import com.practice.blog.filter.SecurityFilter;
import com.practice.blog.repository.UserRepository;
import com.practice.blog.response.message.MessageResponseDto;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static com.practice.blog.response.message.SuccessMessage.POST_LOGIN_SUCCESS;
import static com.practice.blog.response.message.SuccessMessage.POST_SIGNUP_SUCCESS;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(RestDocumentationExtension.class)
public class UserTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LoggingFilter loggingFilter;
    @Autowired
    private SecurityFilter securityFilter;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setupMockMvcForRestDocs(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentationContextProvider) {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentationContextProvider))
                .addFilter(loggingFilter)
                .addFilter(securityFilter)
                .build();
    }

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
        SignupRequestDto signupRequestDto = new SignupRequestDto(email, nickname, password);

        mockMvc.perform(post("/api/user/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signupRequestDto))
                )
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(new MessageResponseDto(POST_SIGNUP_SUCCESS))))
                .andDo(document("User-signup",
                                Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                                Preprocessors.preprocessResponse(Preprocessors.prettyPrint()), // request, response를 가독성 좋게 출력하도록
                                requestFields(
                                        fieldWithPath("email").description("user email field").type(JsonFieldType.STRING),
                                        fieldWithPath("nickname").description("user nickname field").type(JsonFieldType.STRING),
                                        fieldWithPath("password").description("user password field").type(JsonFieldType.STRING)
                                ),
                                responseFields(
                                        fieldWithPath("message").description("success message").type(JsonFieldType.STRING)
                                )
                        )
                )
                .andDo(print());

    }

    private Cookie loginAndGetCookie(String email, String password) throws Exception {
        LoginRequestDto loginRequestDto = new LoginRequestDto(email, password);
        return mockMvc.perform(post("/api/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequestDto))
                )
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(new MessageResponseDto(POST_LOGIN_SUCCESS))))
                .andExpect(cookie().exists("Authorization"))
                .andDo(document("User-login",
                                Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                                Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                                requestFields(
                                        fieldWithPath("email").description("user email field").type(JsonFieldType.STRING),
                                        fieldWithPath("password").description("user password field").type(JsonFieldType.STRING)
                                ),
                                responseFields(
                                        fieldWithPath("message").description("success message").type(JsonFieldType.STRING)
                                )
                        )
                )
                .andDo(print())
                .andReturn().getResponse().getCookie("Authorization");
    }

    private void getProfileByCookie(String email, String nickname, Cookie cookie) throws Exception {
        mockMvc.perform(get("/api/user/profile")
                        .contentType(MediaType.APPLICATION_JSON)
                        .cookie(cookie))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(email))
                .andExpect(jsonPath("$.nickname").value(nickname))
                .andDo(document("User-profile",
                                Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                                Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                                responseFields(
                                        fieldWithPath("email").description("user email info").type(JsonFieldType.STRING),
                                        fieldWithPath("nickname").description("user nickname info").type(JsonFieldType.STRING)
                                )
                        )
                )
                .andDo(print());;
    }
}

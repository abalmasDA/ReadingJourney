package com.readingjourney.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.readingjourney.account.configuration.SecurityConfiguration;
import com.readingjourney.account.controller.LoginController;
import com.readingjourney.account.dto.AuthResponse;
import com.readingjourney.account.dto.LoginDto;
import com.readingjourney.account.jwt.JwtService;
import com.readingjourney.account.service.LoginService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest(LoginController.class)
@Import(SecurityConfiguration.class)
class LoginControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private LoginService loginService;

  @MockBean
  private JwtService jwtService;

  @MockBean
  private UserDetailsService userDetailsService;

  @Autowired
  private ObjectMapper objectMapper;

  private LoginDto loginDto;

  private LoginDto loginDtoInvalidParam;

  private AuthResponse authResponse;


  @BeforeEach
  public void setUp() {
    loginDto = LoginDto.builder()
        .email("test@gmail")
        .password("Test123456test")
        .build();

    loginDtoInvalidParam = LoginDto.builder()
        .email(" ")
        .password("Test123456test")
        .build();

    authResponse = AuthResponse.builder()
        .token("tokenTest")
        .build();

  }

  @Test
  void loginUserTest() throws Exception {
    String requestBody = objectMapper.writeValueAsString(loginDto);
    when(loginService.loginUser(any(LoginDto.class))).thenReturn(authResponse);
    mockMvc.perform(post("/auth/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.token").exists())
        .andExpect(jsonPath("$.token").value("tokenTest"));
  }

  @Test
  void registerUserInvalidNameTest() throws Exception {
    String requestBody = objectMapper.writeValueAsString(loginDtoInvalidParam);
    mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
        .andExpect(status().isBadRequest());
  }

}

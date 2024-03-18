package com.readingjourney.controller;

import static org.hamcrest.Matchers.matchesPattern;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.readingjourney.account.dto.UserDto;
import com.readingjourney.account.jwt.JwtService;
import com.readingjourney.account.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class RegistrationControllerIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private JwtService jwtService;

  @Autowired
  private UserDetailsService userDetailsService;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private ObjectMapper objectMapper;

  private UserDto userDto;

  private UserDto userDtoInvalidParam;


  @BeforeEach
  public void setup() throws Exception {
    userDto = UserDto.builder()
        .firstName("Tester")
        .lastName("Tester")
        .country("Usa")
        .email("test@gmail")
        .password("Test123456test")
        .build();

    userDtoInvalidParam = UserDto.builder()
        .firstName(" ")
        .lastName(" ")
        .country("Usa")
        .email("test@gmail")
        .password("Test123456test")
        .build();

  }

  @Test
  public void registerUserTest() throws Exception {
    String requestBody = objectMapper.writeValueAsString(userDto);
    mockMvc.perform(post("/auth/signup")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.token").exists())
        .andExpect(jsonPath("$.token",
            matchesPattern("^[A-Za-z0-9-_=]+\\.[A-Za-z0-9-_=]+\\.?[A-Za-z0-9-_.+/=]*$")));
  }

  @Test
  public void registerUserInvalidNameTest() throws Exception {
    String requestBody = objectMapper.writeValueAsString(userDtoInvalidParam);
    mockMvc.perform(post("/auth/signup")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
        .andExpect(status().isBadRequest());
  }

}

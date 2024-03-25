package com.readingjourney.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.readingjourney.account.configuration.SecurityConfiguration;
import com.readingjourney.account.controller.RegistrationController;
import com.readingjourney.account.dto.AuthResponse;
import com.readingjourney.account.dto.UserDto;
import com.readingjourney.account.jwt.JwtService;
import com.readingjourney.account.service.RegistrationService;
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

@WebMvcTest(RegistrationController.class)
@Import(SecurityConfiguration.class)
class RegistrationControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private RegistrationService registrationService;

  @MockBean
  private JwtService jwtService;

  @MockBean
  private UserDetailsService userDetailsService;

  @Autowired
  private ObjectMapper objectMapper;

  private UserDto userDto;

  private UserDto userDtoInvalidParam;

  private AuthResponse authResponse;


  @BeforeEach
  public void setUp() {
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

    authResponse = AuthResponse.builder()
        .token("tokenTest")
        .build();

  }

  @Test
  void registerUserTest() throws Exception {
    String requestBody = objectMapper.writeValueAsString(userDto);
    when(registrationService.registerUser(any(UserDto.class))).thenReturn(authResponse);
    mockMvc.perform(post("/auth/signup")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.token").exists())
        .andExpect(jsonPath("$.token").value("tokenTest"));
  }

  @Test
  void registerUserInvalidNameTest() throws Exception {
    String requestBody = objectMapper.writeValueAsString(userDtoInvalidParam);
    mockMvc.perform(MockMvcRequestBuilders.post("/auth/signup")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
        .andExpect(status().isBadRequest());
  }

}

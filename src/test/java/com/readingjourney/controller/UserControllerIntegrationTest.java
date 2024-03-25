package com.readingjourney.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.readingjourney.account.dto.LoginDto;
import com.readingjourney.account.dto.UserDto;
import com.readingjourney.account.entity.Role;
import com.readingjourney.account.entity.User;
import com.readingjourney.account.jwt.JwtService;
import com.readingjourney.account.repository.UserRepository;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
class UserControllerIntegrationTest {

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

  private final String AUTHORIZATION_HEADER = "Authorization";

  private final String TOKEN_PREFIX = "Bearer ";

  private final long USER_ID = 2;

  private UserDto userDto;

  private LoginDto loginDto;

  private String token;

  @BeforeAll
  public void setupOnce() throws Exception {
    User user = new User()
        .builder()
        .id(1L)
        .firstName("userTest")
        .lastName("userTest")
        .country("USA")
        .email("usertest@gmail.com")
        .password(passwordEncoder.encode("passwordtest123"))
        .createdAt(LocalDateTime.now())
        .role(Role.USER)
        .build();
    userRepository.save(user);

    loginDto = LoginDto.builder()
        .email("usertest@gmail.com")
        .password("passwordtest123")
        .build();

    String requestBody = objectMapper.writeValueAsString(loginDto);

    MvcResult result = mockMvc.perform(post("/auth/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
        .andExpect(status().isOk())
        .andReturn();

    String response = result.getResponse().getContentAsString();
    token = JsonPath.parse(response).read("$.token", String.class);

  }

  @BeforeEach
  public void setup() {
    User user = new User()
        .builder()
        .id(USER_ID)
        .firstName("Tester")
        .lastName("Tester")
        .country("Tester")
        .email("tester1234@gmail.com")
        .password("TesterTesterTester12345")
        .createdAt(LocalDateTime.now())
        .role(Role.USER)
        .build();

    userRepository.save(user);

    userDto = UserDto.builder()
        .firstName("Tester")
        .lastName("Tester")
        .country("Tester")
        .email("tester1345@gmail.com")
        .password("Tester123456789///")
        .build();
  }

  @Test
  void allTest() throws Exception {
    mockMvc.perform(get("/users")
            .header(AUTHORIZATION_HEADER, TOKEN_PREFIX + token))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  void findByIdTest() throws Exception {
    mockMvc.perform(get("/users/{id}", USER_ID)
            .header(AUTHORIZATION_HEADER, TOKEN_PREFIX + token))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  void addTest() throws Exception {
    mockMvc.perform(post("/users")
            .header(AUTHORIZATION_HEADER, TOKEN_PREFIX + token)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(userDto)))
        .andExpect(status().isOk());
  }

  @Test
  void updateTest() throws Exception {
    mockMvc.perform(put("/users/{id}", 1)
            .header(AUTHORIZATION_HEADER, TOKEN_PREFIX + token)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(userDto)))
        .andExpect(status().isOk());
  }

  @Test
  void deleteTest() throws Exception {
    mockMvc.perform(delete("/users/{id}", USER_ID)
            .header(AUTHORIZATION_HEADER, TOKEN_PREFIX + token))
        .andExpect(status().isOk());
  }

}

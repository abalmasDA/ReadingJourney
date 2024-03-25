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
import com.readingjourney.account.entity.Role;
import com.readingjourney.account.entity.User;
import com.readingjourney.account.jwt.JwtService;
import com.readingjourney.account.repository.UserRepository;
import com.readingjourney.book.dto.AuthorDto;
import com.readingjourney.book.entity.Author;
import com.readingjourney.book.repository.AuthorRepository;
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
class AuthorControllerIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private AuthorRepository authorRepository;

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

  private final long AUTHOR_ID = 1;

  private AuthorDto authorDto;

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
  public void setup() throws Exception {
    Author author = new Author()
        .builder()
        .id(AUTHOR_ID)
        .firstName("Tester")
        .lastName("Tester")
        .biography("Tester")
        .build();
    authorRepository.save(author);

    authorDto = AuthorDto.builder()
        .firstName("Tester")
        .lastName("Tester")
        .biography("Tester")
        .build();
  }

  @Test
  void allTest() throws Exception {
    mockMvc.perform(get("/authors")
            .header(AUTHORIZATION_HEADER, TOKEN_PREFIX + token))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  void findByIdTest() throws Exception {
    mockMvc.perform(get("/authors/{id}", AUTHOR_ID)
            .header(AUTHORIZATION_HEADER, TOKEN_PREFIX + token))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  void addTest() throws Exception {
    mockMvc.perform(post("/authors")
            .header(AUTHORIZATION_HEADER, TOKEN_PREFIX + token)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(authorDto)))
        .andExpect(status().isOk());
  }

  @Test
  void updateTest() throws Exception {
    mockMvc.perform(put("/authors/{id}", 1)
            .header(AUTHORIZATION_HEADER, TOKEN_PREFIX + token)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(authorDto)))
        .andExpect(status().isOk());
  }

  @Test
  void deleteTest() throws Exception {
    mockMvc.perform(delete("/authors/{id}", AUTHOR_ID)
            .header(AUTHORIZATION_HEADER, TOKEN_PREFIX + token))
        .andExpect(status().isOk());
  }

}


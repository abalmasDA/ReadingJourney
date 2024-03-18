package com.readingjourney.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.matchesPattern;
import static org.hibernate.validator.internal.util.Contracts.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.readingjourney.account.dto.LoginDto;
import com.readingjourney.account.entity.Role;
import com.readingjourney.account.entity.User;
import com.readingjourney.account.jwt.JwtService;
import com.readingjourney.account.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
public class LoginControllerIntegrationTest {

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

  @Value("${jwt.token.secret.key}")
  private String secretKey;

  @Value("${jwt.token.expiration.time}")
  private long expirationTime;

  private LoginDto loginDto;

  private LoginDto loginDtoInvalidParam;

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
  }

  @BeforeEach
  public void setup() throws Exception {
    loginDto = LoginDto.builder()
        .email("usertest@gmail.com")
        .password("passwordtest123")
        .build();

    loginDtoInvalidParam = LoginDto.builder()
        .email("test@gmail.com")
        .password("passwordtest123")
        .build();
  }

  @Test
  public void loginUserTest() throws Exception {
    String expectedEmail = "usertest@gmail.com";
    String requestBody = objectMapper.writeValueAsString(loginDto);

    MvcResult result = mockMvc.perform(post("/auth/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.token").exists())
        .andExpect(jsonPath("$.token",
            matchesPattern("^[A-Za-z0-9-_=]+\\.[A-Za-z0-9-_=]+\\.?[A-Za-z0-9-_.+/=]*$")))
        .andReturn();

    String responseToken = result.getResponse().getContentAsString();
    String token = JsonPath.parse(responseToken).read("$.token");

    Jws<Claims> jwsClaims = Jwts.parserBuilder()
        .setSigningKey(secretKey)
        .build()
        .parseClaimsJws(token);
    Claims claims = jwsClaims.getBody();
    Object alg = jwsClaims.getHeader().get("alg");
    Object subject = claims.getSubject();
    long issuedAtInSeconds = claims.getIssuedAt().getTime() / 1000;
    long expirationTimeInSeconds = claims.getExpiration().getTime() / 1000;

    assertThat(alg).isEqualTo("HS256");
    assertThat(subject).isEqualTo(expectedEmail);
    assertTrue(issuedAtInSeconds <= System.currentTimeMillis() / 1000,
        "Token is in the future.");
    assertTrue(expirationTimeInSeconds >= System.currentTimeMillis() / 1000,
        "Token 'exp' is in the past.");
    assertTrue(expirationTimeInSeconds - issuedAtInSeconds == expirationTime / 1000,
        "Token expiration time does not match the expected duration.");
  }

  @Test
  public void loginUserInvalidNameTest() throws Exception {
    String requestBody = objectMapper.writeValueAsString(loginDtoInvalidParam);
    mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
        .andExpect(status().isForbidden());
  }

}

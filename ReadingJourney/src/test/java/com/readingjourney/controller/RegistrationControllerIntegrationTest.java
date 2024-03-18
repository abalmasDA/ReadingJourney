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
import com.readingjourney.account.dto.UserDto;
import com.readingjourney.account.jwt.JwtService;
import com.readingjourney.account.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

  @Value("${jwt.token.secret.key}")
  private String secretKey;

  @Value("${jwt.token.expiration.time}")
  private long expirationTime;

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
    String expectedEmail = "test@gmail";
    String requestBody = objectMapper.writeValueAsString(userDto);
    MvcResult result = mockMvc.perform(post("/auth/signup")
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
  public void registerUserInvalidNameTest() throws Exception {
    String requestBody = objectMapper.writeValueAsString(userDtoInvalidParam);
    mockMvc.perform(post("/auth/signup")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
        .andExpect(status().isBadRequest());
  }

}

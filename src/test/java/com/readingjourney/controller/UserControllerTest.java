package com.readingjourney.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.readingjourney.account.configuration.SecurityConfiguration;
import com.readingjourney.account.controller.UserController;
import com.readingjourney.account.dto.UserDto;
import com.readingjourney.account.entity.User;
import com.readingjourney.account.jwt.JwtService;
import com.readingjourney.account.service.UserService;
import java.util.Collections;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


@WebMvcTest(UserController.class)
@Import(SecurityConfiguration.class)
class UserControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private UserService userService;

  @MockBean
  private JwtService jwtService;

  @MockBean
  private UserDetailsService userDetailsService;

  @Autowired
  private ObjectMapper objectMapper;

  private final long userId = 1;

  private User user;

  private UserDto userDto;

  private UserDto userDtoInvalidParam;

  @BeforeEach
  public void setUp() {
    user = new User()
        .builder()
        .id(userId)
        .firstName("Tester")
        .lastName("Tester")
        .country("Usa")
        .email("test@gmail.com")
        .password("Test123456test")
        .build();

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
  @WithMockUser(username = "test", password = "test", roles = "USER")
  void findAllUsersTest() throws Exception {
    when(userService.findAll()).thenReturn(Collections.singletonList(user));
    mockMvc.perform(get("/users"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  @WithMockUser(username = "test", password = "test", roles = "USER")
  void findUserByIdTest() throws Exception {
    when(userService.findById(userId)).thenReturn(Optional.of(user));
    mockMvc.perform(get("/users/{id}", userId))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.id").value(userId));
  }

  @Test
  @WithMockUser(username = "test", password = "test", roles = "USER")
  void addUserTest() throws Exception {
    String requestBody = objectMapper.writeValueAsString(userDto);
    when(userService.save(any(UserDto.class))).thenReturn(user);
    mockMvc.perform(post("/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.id").exists())
        .andExpect(jsonPath("$.firstName").value("Tester"))
        .andExpect(jsonPath("$.lastName").value("Tester"))
        .andExpect(jsonPath("$.country").value("Usa"))
        .andExpect(jsonPath("$.email").value("test@gmail.com"))
        .andExpect(jsonPath("$.password").value("Test123456test"));
  }

  @Test
  @WithMockUser(username = "test", password = "test", roles = "USER")
  void addUserInvalidNameTest() throws Exception {
    String requestBody = objectMapper.writeValueAsString(userDtoInvalidParam);
    mockMvc.perform(MockMvcRequestBuilders.post("/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
        .andExpect(status().isBadRequest());
  }

  @Test
  @WithMockUser(username = "test", password = "test", roles = "USER")
  void updateUserTest() throws Exception {
    String requestBody = objectMapper.writeValueAsString(userDto);
    when(userService.update(userId, userDto)).thenReturn(user);
    mockMvc.perform(put("/users/{id}", userId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.id").value(userId))
        .andExpect(jsonPath("$.firstName").value("Tester"))
        .andExpect(jsonPath("$.lastName").value("Tester"))
        .andExpect(jsonPath("$.country").value("Usa"))
        .andExpect(jsonPath("$.email").value("test@gmail.com"))
        .andExpect(jsonPath("$.password").value("Test123456test"));
  }

  @Test
  @WithMockUser(username = "test", password = "test", roles = "USER")
  void updateUserInvalidNameTest() throws Exception {
    String requestBody = objectMapper.writeValueAsString(userDtoInvalidParam);
    mockMvc.perform(MockMvcRequestBuilders.put("/users/{id}", userId)
        .contentType(MediaType.APPLICATION_JSON)
        .content(requestBody)).andExpect(status().isBadRequest());
  }

  @Test
  @WithMockUser(username = "test", password = "test", roles = "USER")
  void deleteUserTest() throws Exception {
    mockMvc.perform(delete("/users/{id}", userId))
        .andExpect(status().isOk());
  }

}


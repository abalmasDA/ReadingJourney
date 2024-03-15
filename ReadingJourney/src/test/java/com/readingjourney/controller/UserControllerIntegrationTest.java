package com.readingjourney.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.readingjourney.account.controller.UserController;
import com.readingjourney.account.dto.UserDto;
import com.readingjourney.account.entity.User;
import com.readingjourney.account.repository.UserRepository;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = UserController.class) // Укажите ваш контроллер здесь
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private ObjectMapper objectMapper;

  private final long userId = 1;

  private UserDto userDto;

  @BeforeEach
  public void setup() {

    User user = new User()
        .builder()
        .id(userId)
        .firstName("Tester")
        .lastName("Tester")
        .country("Tester")
        .email("tester1234@gmail.com")
        .password("TesterTesterTester12345")
        .createdAt(LocalDateTime.now()).build();
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
  public void allTest() throws Exception {
    mockMvc.perform(get("/users"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  public void findByIdTest() throws Exception {
    mockMvc.perform(get("/users/{id}", userId))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  public void addTest() throws Exception {
    mockMvc.perform(post("/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(userDto)))
        .andExpect(status().isOk());
  }

  @Test
  public void updateTest() throws Exception {
    mockMvc.perform(put("/users/{id}", 1)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(userDto)))
        .andExpect(status().isOk());
  }

  @Test
  public void deleteTest() throws Exception {
    mockMvc.perform(delete("/users/{id}", userId))
        .andExpect(status().isOk());
  }

}

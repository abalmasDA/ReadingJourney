package com.readingjourney.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.readingjourney.account.dto.UserDto;
import com.readingjourney.account.entity.User;
import com.readingjourney.account.repository.UserRepository;
import com.readingjourney.configuration.ApplicationConfigurationTest;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ApplicationConfigurationTest.class)
@WebAppConfiguration
public class UserControllerIntegrationTest {

  private MockMvc mockMvc;

  @Autowired
  private WebApplicationContext webApplicationContext;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private ObjectMapper objectMapper;

  private final long userId = 1;

  private UserDto userDto;

  @BeforeEach
  public void setup() {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

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

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

import com.readingjourney.account.controller.UserController;
import com.readingjourney.account.dto.UserDto;
import com.readingjourney.account.entity.User;
import com.readingjourney.account.service.UserService;
import com.readingjourney.book.exception.GlobalExceptionHandler;
import com.readingjourney.configuration.ApplicationConfigurationTest;
import java.util.Collections;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;


@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = ApplicationConfigurationTest.class)
public class UserControllerTest {

  private MockMvc mockMvc;

  @Mock
  private UserService userService;

  @InjectMocks
  private UserController userController;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    mockMvc = MockMvcBuilders.standaloneSetup(userController)
        .setValidator(new LocalValidatorFactoryBean())
        .setControllerAdvice(new GlobalExceptionHandler()).build();
  }

  @Test
  public void findAllUsersTest() throws Exception {
    User user = new User();
    when(userService.findAll()).thenReturn(Collections.singletonList(user));
    mockMvc.perform(get("/users"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  public void findUserByIdTest() throws Exception {
    long id = 1;
    User user = new User();
    user.setId(id);
    when(userService.findById(id)).thenReturn(Optional.of(user));
    mockMvc.perform(get("/users/{id}", id))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.id").value(id));
  }

  @Test
  public void addUserTest() throws Exception {
    UserDto userDto = new UserDto();
    userDto.setFirstName("User");
    userDto.setLastName("User");
    String requestBody = "{\"firstName\":\"User\",\"lastName\":\"User\", \"country\":\"Usa\","
        + " \"email\":\"test@gmail.com\", \"password\":\"Test123456test\"}";
    User user = new User();
    user.setId(1L);
    user.setFirstName("User");
    user.setLastName("User");
    user.setCountry("Usa");
    user.setEmail("test@gmail.com");
    user.setPassword("Test123456test");
    when(userService.save(any(UserDto.class))).thenReturn(user);

    mockMvc.perform(post("/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.id").exists())
        .andExpect(jsonPath("$.firstName").value("User"))
        .andExpect(jsonPath("$.lastName").value("User"))
        .andExpect(jsonPath("$.country").value("Usa"))
        .andExpect(jsonPath("$.email").value("test@gmail.com"))
        .andExpect(jsonPath("$.password").value("Test123456test"));
  }

  @Test
  public void addUserInvalidNameTest() throws Exception {
    String requestBody = "{\"firstName\":\"\",\"lastName\":\"User\", \"country\":\"Usa\","
        + " \"email\":\"test@gmail.com\", \"password\":\"Test123456test\"}";
    mockMvc.perform(MockMvcRequestBuilders.post("/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void updateUserTest() throws Exception {
    long id = 1;
    UserDto userDto = new UserDto();
    userDto.setFirstName("User");
    userDto.setLastName("User");
    userDto.setCountry("Usa");
    userDto.setEmail("test@gmail.com");
    userDto.setPassword("Test123456test");
    String requestBody = "{\"firstName\":\"User\",\"lastName\":\"User\", \"country\":\"Usa\","
        + " \"email\":\"test@gmail.com\", \"password\":\"Test123456test\"}";
    User user = new User();
    user.setId(id);
    user.setFirstName("User");
    user.setLastName("User");
    user.setCountry("Usa");
    user.setEmail("test@gmail.com");
    user.setPassword("Test123456test");
    when(userService.update(id, userDto)).thenReturn(user);

    mockMvc.perform(put("/users/{id}", id)
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.id").value(id))
        .andExpect(jsonPath("$.firstName").value("User"))
        .andExpect(jsonPath("$.lastName").value("User"))
        .andExpect(jsonPath("$.country").value("Usa"))
        .andExpect(jsonPath("$.email").value("test@gmail.com"))
        .andExpect(jsonPath("$.password").value("Test123456test"));
  }

  @Test
  public void updateUserInvalidNameTest() throws Exception {
    long id = 1;
    String requestBody = "{\"firstName\":\"\",\"lastName\":\"User\", \"country\":\"Usa\","
        + " \"email\":\"test@gmail.com\", \"password\":\"Test123456test\"}";
    mockMvc.perform(MockMvcRequestBuilders.put("/users/{id}", id)
        .contentType(MediaType.APPLICATION_JSON)
        .content(requestBody)).andExpect(status().isBadRequest());
  }

  @Test
  public void deleteUserTest() throws Exception {
    long id = 1;
    mockMvc.perform(delete("/users/{id}", id))
        .andExpect(status().isOk());
  }

}


package com.readingjourney.controller;

import com.readingjourney.book.controller.AuthorController;
import com.readingjourney.book.dto.AuthorDto;
import com.readingjourney.book.entity.Author;
import com.readingjourney.book.exception.GlobalExceptionHandler;
import com.readingjourney.book.service.AuthorService;
import com.readingjourney.configuration.ApplicationConfigurationTest;
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.Collections;
import java.util.Optional;
import org.springframework.validation.Validator;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = ApplicationConfigurationTest.class)
public class AuthorControllerTest {

  private MockMvc mockMvc;

  @Mock
  private AuthorService authorService;

  @Mock
  private Validator validator;

  @InjectMocks
  private AuthorController authorController;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    mockMvc = MockMvcBuilders.standaloneSetup(authorController).setValidator(validator)
        .setControllerAdvice(new GlobalExceptionHandler()).build();
  }

  @Test
  public void findAllAuthorsTest() throws Exception {
    Author author = new Author();
    when(authorService.findAll()).thenReturn(Collections.singletonList(author));
    mockMvc.perform(get("/authors"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  public void findAuthorByIdTest() throws Exception {
    long id = 1;
    Author author = new Author();
    author.setId(id);
    when(authorService.findById(id)).thenReturn(Optional.of(author));
    mockMvc.perform(get("/authors/{id}", id))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.id").value(id));
  }

  @Test
  public void addAuthorTest() throws Exception {
    AuthorDto authorDto = new AuthorDto();
    authorDto.setFirstName("Author");
    authorDto.setLastName("Author");
    String requestBody = "{\"firstName\":\"Author\",\"lastName\":\"Author\"}";
    Author author = new Author();
    author.setId(1L);
    when(authorService.save(any(AuthorDto.class))).thenReturn(author);

    mockMvc.perform(post("/authors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.id").exists());
  }

  @Test
  public void addAuthorInvalidNameTest() throws Exception {
    String requestBody = "{\"firstName\":\"f\", \"lastName\":\"Test\", \"biography\":\"Test\"}";
    mockMvc.perform(MockMvcRequestBuilders.post("/authors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }

  @Test
  public void updateAuthorTest() throws Exception {
    long id = 1;
    AuthorDto authorDto = new AuthorDto();
    authorDto.setFirstName("Author");
    authorDto.setLastName("Author");
    String requestBody = "{\"firstName\":\"Author\",\"lastName\":\"Author\"}";
    Author author = new Author();
    author.setId(id);
    author.setFirstName("Author");
    author.setLastName("Author");
    when(authorService.update(id, authorDto)).thenReturn(author);

    mockMvc.perform(put("/authors/{id}", id)
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.id").value(id))
        .andExpect(jsonPath("$.firstName").value("Author"))
        .andExpect(jsonPath("$.lastName").value("Author"));
  }

  @Test
  public void updateAuthorInvalidNameTest() throws Exception {
    long id = 1;
    String requestBody = "{\"name\":\"\"}";
    mockMvc.perform(MockMvcRequestBuilders.put("/authors/{id}", id)
        .contentType(MediaType.APPLICATION_JSON)
        .content(requestBody)).andExpect(MockMvcResultMatchers.status().isBadRequest());
  }

  @Test
  public void deleteAuthorTest() throws Exception {
    long id = 1;
    mockMvc.perform(delete("/authors/{id}", id))
        .andExpect(status().isOk());
  }

}


package com.readingjourney.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.readingjourney.book.dto.AuthorDto;
import com.readingjourney.book.entity.Author;
import com.readingjourney.book.repository.AuthorRepository;
import com.readingjourney.configuration.ApplicationConfigurationTest;
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
public class AuthorControllerIntegrationTest {

  private MockMvc mockMvc;

  @Autowired
  private WebApplicationContext webApplicationContext;

  @Autowired
  private AuthorRepository authorRepository;

  @Autowired
  private ObjectMapper objectMapper;

  private final long authorId = 1;

  private AuthorDto authorDto;

  @BeforeEach
  public void setup() {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

    Author author = new Author()
        .builder()
        .id(authorId)
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
  public void allTest() throws Exception {
    mockMvc.perform(get("/authors"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  public void findByIdTest() throws Exception {
    mockMvc.perform(get("/authors/{id}", authorId))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  public void addTest() throws Exception {
    mockMvc.perform(post("/authors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(authorDto)))
        .andExpect(status().isOk());
  }

  @Test
  public void updateTest() throws Exception {
    mockMvc.perform(put("/authors/{id}", 1)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(authorDto)))
        .andExpect(status().isOk());
  }

  @Test
  public void deleteTest() throws Exception {
    mockMvc.perform(delete("/authors/{id}", authorId))
        .andExpect(status().isOk());
  }

}


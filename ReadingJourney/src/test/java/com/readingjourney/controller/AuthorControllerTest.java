package com.readingjourney.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.readingjourney.account.configuration.SecurityConfiguration;
import com.readingjourney.account.jwt.JwtService;
import com.readingjourney.book.controller.AuthorController;
import com.readingjourney.book.dto.AuthorDto;
import com.readingjourney.book.entity.Author;
import com.readingjourney.book.service.AuthorService;
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
import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthorController.class)
@Import(SecurityConfiguration.class)
public class AuthorControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private AuthorService authorService;

  @MockBean
  private JwtService jwtService;

  @MockBean
  private UserDetailsService userDetailsService;

  @Autowired
  private ObjectMapper objectMapper;

  private final long authorId = 1;

  private Author author;

  private AuthorDto authorDto;

  private AuthorDto authorDtoInvalidParam;

  @BeforeEach
  public void setup() {
    author = new Author()
        .builder()
        .id(authorId)
        .firstName("Tester")
        .lastName("Tester")
        .biography("Tester")
        .build();

    authorDto = AuthorDto.builder()
        .firstName("Tester")
        .lastName("Tester")
        .biography("Tester")
        .build();

    authorDtoInvalidParam = AuthorDto.builder()
        .firstName(" ")
        .lastName("T")
        .build();
  }

  @Test
  @WithMockUser(username = "test", password = "test", roles = "USER")
  public void findAllAuthorsTest() throws Exception {
    when(authorService.findAll()).thenReturn(Collections.singletonList(author));
    mockMvc.perform(get("/authors"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  @WithMockUser(username = "test", password = "test", roles = "USER")
  public void findAuthorByIdTest() throws Exception {
    when(authorService.findById(authorId)).thenReturn(Optional.of(author));
    mockMvc.perform(get("/authors/{id}", authorId))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.id").value(authorId));
  }

  @Test
  @WithMockUser(username = "test", password = "test", roles = "USER")
  public void addAuthorTest() throws Exception {
    String requestBody = objectMapper.writeValueAsString(authorDto);
    when(authorService.save(any(AuthorDto.class))).thenReturn(author);
    mockMvc.perform(MockMvcRequestBuilders.post("/authors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  @WithMockUser(username = "test", password = "test", roles = "USER")
  public void addAuthorInvalidNameTest() throws Exception {
    String requestBody = objectMapper.writeValueAsString(authorDtoInvalidParam);
    mockMvc.perform(post("/authors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
        .andExpect(status().isBadRequest());
  }

  @Test
  @WithMockUser(username = "test", password = "test", roles = "USER")
  public void updateAuthorTest() throws Exception {
    String requestBody = objectMapper.writeValueAsString(authorDto);
    when(authorService.update(any(Long.class), any(AuthorDto.class))).thenReturn(author);
    mockMvc.perform(put("/authors/{id}", authorId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.id").value(authorId))
        .andExpect(jsonPath("$.firstName").value("Tester"))
        .andExpect(jsonPath("$.lastName").value("Tester"));
  }

  @Test
  @WithMockUser(username = "test", password = "test", roles = "USER")
  public void updateAuthorInvalidNameTest() throws Exception {
    String requestBody = objectMapper.writeValueAsString(authorDtoInvalidParam);
    mockMvc.perform(put("/authors/{id}", authorId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
        .andExpect(status().isBadRequest());
  }

  @Test
  @WithMockUser(username = "test", password = "test", roles = "USER")
  public void deleteAuthorTest() throws Exception {
    mockMvc.perform(delete("/authors/{id}", authorId))
        .andExpect(status().isOk());
  }

}


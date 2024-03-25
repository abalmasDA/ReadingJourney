package com.readingjourney.controller;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
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
import com.readingjourney.account.jwt.JwtService;
import com.readingjourney.book.controller.BookController;
import com.readingjourney.book.dto.BookDto;
import com.readingjourney.book.entity.Book;
import com.readingjourney.book.service.BookService;
import java.time.LocalDate;
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

@WebMvcTest(BookController.class)
@Import(SecurityConfiguration.class)
class BookControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private BookService bookService;

  @MockBean
  private JwtService jwtService;

  @MockBean
  private UserDetailsService userDetailsService;

  @Autowired
  private ObjectMapper objectMapper;

  private final long bookId = 1;

  private final long authorId = 1;

  private Book book;

  private BookDto bookDto;

  private BookDto bookDtoUpdated;

  @BeforeEach
  public void setUp() {
    book = new Book()
        .builder()
        .id(bookId)
        .build();

    bookDto = BookDto.builder()
        .title("Test Book")
        .rating(5L)
        .yearPublication(LocalDate.of(2022, 1, 1))
        .numberPages(200)
        .genre("Fiction")
        .format("Paperback")
        .edition("First")
        .isbn(1234567890123L)
        .build();

    bookDtoUpdated = BookDto.builder()
        .title("Test Book Updated")
        .rating(2L)
        .yearPublication(LocalDate.of(2022, 1, 1))
        .numberPages(100)
        .genre("Non-Fiction")
        .format("E-book")
        .edition("First")
        .isbn(1234567890123L)
        .build();

  }

  @Test
  @WithMockUser(username = "test", password = "test", roles = "USER")
  void findAllBooksTest() throws Exception {
    when(bookService.findAll()).thenReturn(Collections.singletonList(book));
    mockMvc.perform(get("/books"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  @WithMockUser(username = "test", password = "test", roles = "USER")
  void findBookByIdTest() throws Exception {
    when(bookService.findById(bookId)).thenReturn(Optional.of(book));
    mockMvc.perform(get("/books/{id}", bookId))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.id").exists());
  }

  @Test
  @WithMockUser(username = "test", password = "test", roles = "USER")
  void addBookTest() throws Exception {
    String requestBody = objectMapper.writeValueAsString(bookDto);
    when(bookService.save(eq(authorId), any(BookDto.class))).thenReturn(book);
    mockMvc.perform(post("/books/{id}", authorId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.id").exists());
  }

  @Test
  @WithMockUser(username = "test", password = "test", roles = "USER")
  void updateBookTest() throws Exception {
    String requestBody = objectMapper.writeValueAsString(bookDtoUpdated);
    when(bookService.update(eq(bookId), any(BookDto.class))).thenReturn(book);
    mockMvc.perform(put("/books/{id}", bookId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.id").exists());
  }

  @Test
  @WithMockUser(username = "test", password = "test", roles = "USER")
  void deleteBookTest() throws Exception {
    mockMvc.perform(delete("/books/{id}", bookId))
        .andExpect(status().isOk());
  }

}

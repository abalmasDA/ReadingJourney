package com.readingjourney.controller;


import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.readingjourney.book.controller.BookController;
import com.readingjourney.book.dto.BookDto;
import com.readingjourney.book.entity.Book;
import com.readingjourney.book.exception.GlobalExceptionHandler;
import com.readingjourney.book.service.BookService;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = ApplicationConfigurationTest.class)
public class BookControllerTest {

  private MockMvc mockMvc;

  @Mock
  private BookService bookService;

  @InjectMocks
  private BookController bookController;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    mockMvc = MockMvcBuilders.standaloneSetup(bookController)
        .setValidator(new LocalValidatorFactoryBean())
        .setControllerAdvice(new GlobalExceptionHandler()).build();
  }

  @Test
  public void findAllBooksTest() throws Exception {
    Book book = new Book();
    when(bookService.findAll()).thenReturn(Collections.singletonList(book));
    mockMvc.perform(get("/books"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  public void findBookByIdTest() throws Exception {
    long id = 1;
    Book book = new Book();
    book.setId(id);
    when(bookService.findById(id)).thenReturn(Optional.of(book));
    mockMvc.perform(get("/books/{id}", id))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.id").exists());
  }

  @Test
  public void addBookTest() throws Exception {
    long authorId = 1;
    long bookId = 1;
    String requestBody = "{\"title\":\"Book Title\",\"rating\":5,"
        + "\"yearPublication\":\"2022-01-01\",\"numberPages\":200,\"genre\":\"Fiction\","
        + "\"format\":\"Hardcover\",\"edition\":\"First\",\"isbn\":1234567890123}";
    Book book = new Book();
    book.setId(bookId);
    when(bookService.save(eq(authorId), any(BookDto.class))).thenReturn(book);

    mockMvc.perform(post("/books/{id}", authorId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.id").exists());
  }

  @Test
  public void updateBookTest() throws Exception {
    long id = 1;
    String requestBody = "{\"title\":\"Updated Book Title\",\"rating\":5,"
        + "\"yearPublication\":\"2023-01-01\",\"numberPages\":250,\"genre\":\"Non-fiction\","
        + "\"format\":\"Paperback\",\"edition\":\"Second\",\"isbn\":9876543210987}";
    Book book = new Book();
    book.setId(id);
    when(bookService.update(eq(id), any(BookDto.class))).thenReturn(book);

    mockMvc.perform(put("/books/{id}", id)
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.id").exists());
  }

  @Test
  public void deleteBookTest() throws Exception {
    long id = 1;
    mockMvc.perform(delete("/books/{id}", id))
        .andExpect(status().isOk());
  }

}

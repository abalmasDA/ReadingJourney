package com.readingjourney.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.readingjourney.book.dto.BookDto;
import com.readingjourney.book.entity.Author;
import com.readingjourney.book.entity.Book;
import com.readingjourney.book.repository.AuthorRepository;
import com.readingjourney.book.repository.BookRepository;
import com.readingjourney.configuration.ApplicationConfigurationTest;
import java.time.LocalDate;
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
public class BookControllerIntegrationTest {

  private MockMvc mockMvc;

  @Autowired
  private WebApplicationContext webApplicationContext;

  @Autowired
  private BookRepository bookRepository;

  @Autowired
  private AuthorRepository authorRepository;

  @Autowired
  private ObjectMapper objectMapper;

  private final long bookId = 1;

  private final long authorId = 1;

  private BookDto bookDto;

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

    Book book = new Book()
        .builder()
        .id(bookId)
        .author(authorRepository.findById(authorId).get())
        .title("Tester")
        .rating(5L)
        .yearPublication(LocalDate.now())
        .numberPages(10)
        .genre("Tester")
        .format("Tester")
        .edition("Tester")
        .isbn(1234567890123L)
        .build();
    bookRepository.save(book);

    bookDto = BookDto
        .builder()
        .title("Tester")
        .rating(7L)
        .yearPublication(LocalDate.now())
        .numberPages(10)
        .genre("Tester12345")
        .format("Tester")
        .edition("Tester")
        .isbn(1334567890123L)
        .build();
  }

  @Test
  public void allTest() throws Exception {
    mockMvc.perform(get("/books"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  public void findByIdTest() throws Exception {
    mockMvc.perform(get("/books/{id}", bookId))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  public void addTest() throws Exception {
    mockMvc.perform(post("/books/{id}", authorId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(bookDto)))
        .andExpect(status().isOk());
  }

  @Test
  public void updateTest() throws Exception {
    mockMvc.perform(put("/books/{id}", bookId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(bookDto)))
        .andExpect(status().isOk());
  }

  @Test
  public void deleteTest() throws Exception {
    mockMvc.perform(delete("/books/{id}", bookId))
        .andExpect(status().isOk());
  }

}

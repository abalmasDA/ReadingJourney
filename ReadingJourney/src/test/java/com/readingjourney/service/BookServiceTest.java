package com.readingjourney.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.readingjourney.book.dto.BookDto;
import com.readingjourney.book.entity.Author;
import com.readingjourney.book.entity.Book;
import com.readingjourney.book.exception.AuthorNotFoundException;
import com.readingjourney.book.exception.BookNotFoundException;
import com.readingjourney.book.mapper.BookMapper;
import com.readingjourney.book.repository.AuthorRepository;
import com.readingjourney.book.repository.BookRepository;
import com.readingjourney.book.service.BookService;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

  @Mock
  private BookRepository bookRepository;

  @Mock
  private AuthorRepository authorRepository;

  @Mock
  private BookMapper bookMapper;

  @InjectMocks
  private BookService bookService;

  private BookDto bookDto;
  private Book book;
  private Author author;
  private long bookId;

  @BeforeEach
  void setUp() {
    bookId = 1L;
    bookDto = new BookDto();
    book = new Book();
    author = new Author();
    author.setId(1L);
    book.setAuthor(author);
  }

  @Test
  void findAllTest() {
    int expected = 1;
    when(bookRepository.findAll()).thenReturn(Collections.singletonList(book));
    List<Book> result = bookService.findAll();
    assertThat(result).isNotNull();
    Assertions.assertThat(result.size()).isEqualTo(expected);
    verify(bookRepository).findAll();
  }

  @Test
  void findByIdTest() {
    when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));
    Optional<Book> result = bookService.findById(bookId);
    assertThat(result).isPresent();
    verify(bookRepository).findById(bookId);
  }

  @Test
  void findByIdExceptionTest() {
    when(bookRepository.findById(anyLong())).thenReturn(Optional.empty());
    assertThatThrownBy(() -> bookService.findById(bookId))
        .isInstanceOf(BookNotFoundException.class);
  }

  @Test
  void saveTest() {
    long authorId = 1;
    when(authorRepository.findById(authorId)).thenReturn(Optional.of(author));
    when(bookMapper.toEntity(bookDto)).thenReturn(book);
    when(bookRepository.save(any(Book.class))).thenReturn(book);

    Book bookToBeSaved = bookService.save(authorId, bookDto);
    assertThat(bookToBeSaved).isNotNull();
    verify(authorRepository).findById(authorId);
    verify(bookRepository).save(any(Book.class));
  }


  @Test
  void saveExceptionTest() {
    long authorId = 1;
    when(authorRepository.findById(anyLong())).thenReturn(Optional.empty());
    assertThatThrownBy(() -> bookService.save(authorId, bookDto))
        .isInstanceOf(AuthorNotFoundException.class);
  }

  @Test
  void updateTest() {
    when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));
    when(bookRepository.save(any(Book.class))).thenReturn(book);
    Book bookToBeUpdated = bookService.update(bookId, bookDto);
    assertThat(bookToBeUpdated).isNotNull();
    verify(bookRepository).findById(bookId);
    verify(bookRepository).save(book);
  }

  @Test
  void updateExceptionTest() {
    when(bookRepository.findById(anyLong())).thenReturn(Optional.empty());
    assertThatThrownBy(() -> bookService.update(bookId, bookDto))
        .isInstanceOf(BookNotFoundException.class);
  }

  @Test
  void deleteTest() {
    when(bookRepository.existsById(anyLong())).thenReturn(true);
    doNothing().when(bookRepository).deleteById(anyLong());
    bookService.delete(bookId);
    verify(bookRepository).deleteById(bookId);
  }

  @Test
  void deleteExceptionTest() {
    when(bookRepository.existsById(anyLong())).thenReturn(false);
    assertThatThrownBy(() -> bookService.delete(bookId))
        .isInstanceOf(BookNotFoundException.class);
  }

  @Test
  void deleteAllTest() {
    doNothing().when(bookRepository).deleteAll();
    bookService.deleteAll();
    verify(bookRepository).deleteAll();
  }

}

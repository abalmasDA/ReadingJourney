package com.readingjourney.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.readingjourney.book.dto.AuthorDto;
import com.readingjourney.book.entity.Author;
import com.readingjourney.book.exception.AuthorNotFoundException;
import com.readingjourney.book.mapper.AuthorMapper;
import com.readingjourney.book.repository.AuthorRepository;
import com.readingjourney.book.service.AuthorService;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AuthorServiceTest {

  @Mock
  private AuthorRepository authorRepository;
  @Mock
  private AuthorMapper authorMapper;
  @InjectMocks
  private AuthorService authorService;
  private AuthorDto authorDto;
  private Author author;
  private long authorId;

  @BeforeEach
  void setUp() {
    authorId = 1L;
    authorDto = new AuthorDto();
    author = new Author();
    author.setId(1L);
  }

  @Test
  void findAllTest() {
    int expected = 1;
    when(authorRepository.findAll()).thenReturn(Collections.singletonList(author));
    List<Author> authors = authorService.findAll();
    assertThat(authors).hasSize(expected);
    verify(authorRepository).findAll();
  }

  @Test
  void findByIdTest() {
    when(authorRepository.findById(authorId)).thenReturn(Optional.of(author));
    Optional<Author> author = authorService.findById(authorId);
    assertThat(author).isPresent();
    assertThat(author.get().getId()).isEqualTo(authorId);
    verify(authorRepository).findById(authorId);
  }

  @Test
  void findByIdThrowExceptionTest() {
    when(authorRepository.findById(anyLong())).thenReturn(Optional.empty());
    assertThatThrownBy(() -> authorService.findById(authorId))
        .isInstanceOf(AuthorNotFoundException.class);
    verify(authorRepository).findById(authorId);
  }

  @Test
  void saveTest() {
    when(authorMapper.toEntity(any(AuthorDto.class))).thenReturn(author);
    when(authorRepository.save(any(Author.class))).thenReturn(author);
    Author authorToBeSaved = authorService.save(authorDto);
    assertThat(authorToBeSaved).isNotNull();
    verify(authorRepository).save(author);
    verify(authorMapper).toEntity(authorDto);
  }

  @Test
  void updateTest() {
    when(authorRepository.findById(authorId)).thenReturn(Optional.of(author));
    when(authorRepository.save(any(Author.class))).thenReturn(author);
    Author updatedAuthor = authorService.update(authorId, authorDto);
    assertThat(updatedAuthor).isNotNull();
    verify(authorRepository).findById(authorId);
    verify(authorRepository).save(author);
  }

  @Test
  void updateThrowExceptionTest() {
    when(authorRepository.findById(anyLong())).thenReturn(Optional.empty());
    assertThatThrownBy(() -> authorService.update(authorId, authorDto))
        .isInstanceOf(AuthorNotFoundException.class);
    verify(authorRepository).findById(authorId);
  }

  @Test
  void deleteTest() {
    when(authorRepository.existsById(authorId)).thenReturn(true);
    doNothing().when(authorRepository).deleteById(authorId);
    authorService.delete(authorId);
    verify(authorRepository).deleteById(authorId);
  }

  @Test
  void deleteThrowExceptionTest() {
    when(authorRepository.existsById(anyLong())).thenReturn(false);
    assertThatThrownBy(() -> authorService.delete(authorId))
        .isInstanceOf(AuthorNotFoundException.class);
    verify(authorRepository, never()).deleteById(authorId);
  }

  @Test
  void deleteAllTest() {
    doNothing().when(authorRepository).deleteAll();
    authorService.deleteAll();
    verify(authorRepository).deleteAll();
  }

}


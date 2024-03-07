package com.readingjourney.book.service;

import com.readingjourney.book.aspects.Loggable;
import com.readingjourney.book.dto.AuthorDto;
import com.readingjourney.book.entity.Author;
import com.readingjourney.book.exception.AuthorNotFoundException;
import com.readingjourney.book.mapper.AuthorMapper;
import com.readingjourney.book.repository.AuthorRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

/**
 * The AuthorService class provides methods for managing authors.
 */
@Service
public class AuthorService {

  private final AuthorRepository authorRepository;
  private final AuthorMapper authorMapper;

  public AuthorService(AuthorRepository authorRepository, AuthorMapper authorMapper) {
    this.authorRepository = authorRepository;
    this.authorMapper = authorMapper;
  }

  public List<Author> findAll() {
    return authorRepository.findAll();
  }

  @Loggable
  public Optional<Author> findById(long id) {
    return Optional.ofNullable(authorRepository.findById(id)
        .orElseThrow(() -> new AuthorNotFoundException("Author with id " + id + " not found")));
  }

  /**
   * Saves the Author from the given AuthorDto.
   *
   * @param authorDto the AuthorDto to be saved
   * @return the saved Author
   */
  @Loggable
  public Author save(AuthorDto authorDto) {
    Author author = authorMapper.toEntity(authorDto);
    authorRepository.save(author);
    return author;
  }

  /**
   * Update an author with the given ID using the information from the AuthorDto.
   *
   * @param id        the ID of the author to be updated
   * @param authorDto the data transfer object containing the updated author information
   * @return the updated author entity
   */
  public Author update(long id, AuthorDto authorDto) {
    return authorRepository.findById(id).map(author1 -> {
      author1.setFirstName(authorDto.getFirstName());
      author1.setLastName(authorDto.getLastName());
      author1.setBiography(authorDto.getBiography());
      return authorRepository.save(author1);
    }).orElseThrow(() -> new AuthorNotFoundException("Author with id " + id + " not found"));
  }

  /**
   * Deletes an author by their ID if it exists, otherwise throws an AuthorNotFoundException.
   *
   * @param id the ID of the author to be deleted
   */
  public void delete(long id) {
    if (authorRepository.existsById(id)) {
      authorRepository.deleteById(id);
    } else {
      throw new AuthorNotFoundException("Author with id " + id + " not found");
    }
  }

  public void deleteAll() {
    authorRepository.deleteAll();
  }

}




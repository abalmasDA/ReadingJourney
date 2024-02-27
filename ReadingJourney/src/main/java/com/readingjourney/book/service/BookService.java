package com.readingjourney.book.service;

import com.readingjourney.book.dto.BookDto;
import com.readingjourney.book.entity.Book;
import com.readingjourney.book.exception.AuthorNotFoundException;
import com.readingjourney.book.exception.BookNotFoundException;
import com.readingjourney.book.mapper.BookMapper;
import com.readingjourney.book.repository.AuthorRepository;
import com.readingjourney.book.repository.BookRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

/**
 * Service for managing books, including operations like finding all books, finding a book by its
 * ID, saving a new book, updating a book, deleting a book by its ID, and deleting all books.
 */
@Service
public class BookService {

  private final BookRepository bookRepository;
  private final AuthorRepository authorRepository;
  private final BookMapper bookMapper;

  public BookService(BookRepository bookRepository, AuthorRepository authorRepository,
      BookMapper bookMapper) {
    this.bookRepository = bookRepository;
    this.authorRepository = authorRepository;
    this.bookMapper = bookMapper;
  }

  public List<Book> findAll() {
    return bookRepository.findAll();
  }

  public Optional<Book> findById(long id) {
    return Optional.ofNullable(bookRepository.findById(id)
        .orElseThrow(() -> new BookNotFoundException("Book with id " + id + " not found")));
  }

  /**
   * Save a book for a given author.
   *
   * @param authorId the ID of the author
   * @param bookDto  the data transfer object for the book
   * @return the saved book
   */
  public Book save(long authorId, BookDto bookDto) {
    return authorRepository.findById(authorId).map(author -> {
      Book book = bookMapper.toEntity(bookDto);
      book.setAuthor(author);
      bookRepository.save(book);
      return book;
    }).orElseThrow(() -> new AuthorNotFoundException("Author with id " + authorId + " not found"));
  }

  /**
   * Updates a book with the given id using the information from the provided BookDto.
   *
   * @param id      the id of the book to be updated
   * @param bookDto the data transfer object containing the updated information for the book
   * @return the updated book
   */
  public Book update(long id, BookDto bookDto) {
    return bookRepository.findById(id).map(book1 -> {
      book1.setTitle(bookDto.getTitle());
      book1.setYearPublication(bookDto.getYearPublication());
      book1.setNumberPages(bookDto.getNumberPages());
      book1.setGenre(bookDto.getGenre());
      book1.setFormat(bookDto.getFormat());
      book1.setEdition(bookDto.getEdition());
      book1.setIsbn(bookDto.getIsbn());
      return bookRepository.save(book1);
    }).orElseThrow(() -> new BookNotFoundException("Book with id " + id + " not found"));
  }

  /**
   * Deletes a book by its ID if it exists, otherwise throws a BookNotFoundException.
   *
   * @param id the ID of the book to be deleted
   */
  public void delete(long id) {
    if (bookRepository.existsById(id)) {
      bookRepository.deleteById(id);
    } else {
      throw new BookNotFoundException("Book with id " + id + " not found");
    }
  }

  public void deleteAll() {
    bookRepository.deleteAll();
  }

}

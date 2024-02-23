package com.readingjourney.book.service;

import com.readingjourney.book.dto.BookDto;
import com.readingjourney.book.entity.Book;
import com.readingjourney.book.mapper.BookMapper;
import com.readingjourney.book.repository.AuthorRepository;
import com.readingjourney.book.repository.BookDetailsRepository;
import com.readingjourney.book.repository.BookRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class BookService {

  private final BookRepository bookRepository;
  private final BookDetailsRepository bookDetailsRepository;
  private final AuthorRepository authorRepository;
  private final BookMapper bookMapper;

  public BookService(BookRepository bookRepository, BookDetailsRepository bookDetailsRepository,
      AuthorRepository authorRepository, BookMapper bookMapper) {
    this.bookRepository = bookRepository;
    this.bookDetailsRepository = bookDetailsRepository;
    this.authorRepository = authorRepository;
    this.bookMapper = bookMapper;
  }


  public List<Book> findAll() {
    return bookRepository.findAll();
  }

  public Optional<Book> findById(long id) {
    return bookRepository.findById(id);
  }

  public Book add(BookDto bookDto) {
    Book book = bookMapper.toEntity(bookDto);
    bookRepository.save(book);
    return book;
  }

  public Book update(long id, BookDto bookDto) {
    Book book = bookMapper.toEntity(bookDto);
    book.setId(id);
    bookRepository.save(book);
    return book;
  }

  public void delete(long id) {
    bookRepository.deleteById(id);
  }


}

package com.readingjourney.book.controller;

import com.readingjourney.book.dto.BookDto;
import com.readingjourney.book.entity.Book;
import com.readingjourney.book.service.BookService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing books. This controller provides endpoints for retrieving, adding,
 * updating, and deleting books.
 */
@RestController
@RequestMapping("/books")
public class BookController {

  private final BookService bookService;

  public BookController(BookService bookService) {
    this.bookService = bookService;
  }

  @GetMapping
  public List<Book> findAll() {
    return bookService.findAll();
  }

  @GetMapping("/{id}")
  public Optional<Book> findById(@PathVariable("id") long id) {
    return bookService.findById(id);
  }

  @PostMapping("/{id}")
  public Book add(@PathVariable("id") long authorId, @Valid @RequestBody BookDto bookDto) {
    return bookService.save(authorId, bookDto);
  }

  @PutMapping("/{id}")
  public Book update(@PathVariable("id") long id, @Valid @RequestBody BookDto bookDto) {
    return bookService.update(id, bookDto);
  }

  @DeleteMapping("{id}")
  public void delete(@PathVariable("id") long id) {
    bookService.delete(id);
  }

}

package com.readingjourney.book.controller;

import com.readingjourney.book.dto.BookDto;
import com.readingjourney.book.entity.Book;
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

@RestController
@RequestMapping("/books")
public class BookController {

  private final BookService bookService;

  public BookController(BookService bookService) {
    this.bookService = bookService;
  }

  @GetMapping
  public List<Book> getAll() {
    return bookService.findAll();
  }

  @GetMapping("/{id}")
  public Optional<Book> getById(@PathVariable long id) {
    return bookService.findById(id);
  }

  @PostMapping()
  public Book add(@RequestBody BookDto bookDto) {
    return bookService.add(bookDto);
  }

  @PutMapping("/{id}")
  public Book update(@PathVariable long id, @RequestBody BookDto bookDto) {
    return bookService.update(id, bookDto);
  }

  @DeleteMapping("{id}")
  public void delete(@PathVariable long id) {
    bookService.delete(id);
  }

}

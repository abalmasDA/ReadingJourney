package com.readingjourney.book.service;

import com.readingjourney.book.repository.BookRepository;
import org.springframework.stereotype.Service;

@Service
public class BookService {

  private final BookRepository bookRepository;

  public BookService(BookRepository bookRepository) {
    this.bookRepository = bookRepository;
  }




}

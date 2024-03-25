package com.readingjourney.book.exception;

public class BookNotFoundException extends RuntimeException {

  private static final String MESSAGE_NOT_FOUND_FORMAT = "Book with id %d not found";

  public BookNotFoundException(long id) {
    super(String.format(MESSAGE_NOT_FOUND_FORMAT, id));
  }

}

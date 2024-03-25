package com.readingjourney.book.exception;

public class AuthorNotFoundException extends RuntimeException {

  private static final String MESSAGE_NOT_FOUND_FORMAT = "Author with id %d not found";

  public AuthorNotFoundException(long id) {
    super(String.format(MESSAGE_NOT_FOUND_FORMAT, id));
  }

}


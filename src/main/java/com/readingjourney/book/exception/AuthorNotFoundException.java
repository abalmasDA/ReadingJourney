package com.readingjourney.book.exception;

/**
 * Exception thrown when an author is not found.
 */
public class AuthorNotFoundException extends RuntimeException {

  private static final String MESSAGE_NOT_FOUND_FORMAT = "Author with id %d not found";

  public AuthorNotFoundException(long id) {
    super(String.format(MESSAGE_NOT_FOUND_FORMAT, id));
  }

}


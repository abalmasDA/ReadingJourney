package com.readingjourney.account.exception;

public class UserNotFoundException extends RuntimeException {

  private static final String MESSAGE_NOT_FOUND_FORMAT_ID = "User with id %d not found";
  private static final String MESSAGE_NOT_FOUND_FORMAT_EMAIL = "User with email %s not found";

  public UserNotFoundException(String email) {
    super(String.format(MESSAGE_NOT_FOUND_FORMAT_EMAIL, email));
  }

  public UserNotFoundException(long id) {
    super(String.format(MESSAGE_NOT_FOUND_FORMAT_ID, id));
  }
}

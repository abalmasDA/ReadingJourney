package com.readingjourney.notification.exception;

/**
 * Custom exception class for handling email sending errors.
 */
public class EmailSendingException extends RuntimeException {

  public EmailSendingException(String message) {
    super(message);
  }

  public EmailSendingException(String message, Throwable cause) {
    super(message, cause);
  }

}
package com.readingjourney.book.exception;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Global exception handler for handling exceptions thrown by the application.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

  /**
   * Handles exceptions of type Exception by returning an Internal Server Error response.
   *
   * @param ex the exception that was thrown
   * @return a ResponseEntity containing the error details and status code
   */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<Object> handleExceptionErrors(Exception ex) {
    Map<String, Object> body = new HashMap<>();
    body.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
    body.put("error", "Internal Server Error");
    body.put("message",
        "Oops! Something went wrong:( We're working to fix it! Please try again later:");
    return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(AuthorNotFoundException.class)
  public ResponseEntity<Object> handleAuthorNotFoundException(AuthorNotFoundException ex) {
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(BookNotFoundException.class)
  public ResponseEntity<Object> handleBookNotFoundException(BookNotFoundException ex) {
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
  }

  /**
   * Handles MethodArgumentNotValidException by returning a Bad Request response with validation
   * errors.
   *
   * @param ex the exception that was thrown
   * @return a ResponseEntity containing the validation errors and status code
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult().getAllErrors().forEach((error) -> {
      String fieldName = ((FieldError) error).getField();
      String errorMessage = error.getDefaultMessage();
      errors.put(fieldName, errorMessage);
    });
    return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
  }

}

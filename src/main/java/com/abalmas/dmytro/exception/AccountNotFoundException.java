package main.java.com.abalmas.dmytro.exception;

public class AccountNotFoundException extends RuntimeException {

  public AccountNotFoundException(String message) {
    super(message);
  }

}

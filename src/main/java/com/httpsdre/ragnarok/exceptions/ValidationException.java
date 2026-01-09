package com.httpsdre.ragnarok.exceptions;

public class ValidationException extends RuntimeException {
  public ValidationException(String message) {
    super(message);
  }
  public ValidationException() {
    super("Validation exception!");
  }
}

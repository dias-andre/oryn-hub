package com.httpsdre.ragnarok.exceptions;

public class UnauthorizedException extends RuntimeException {
  public UnauthorizedException(String message) {
    super(message);
  }

  public UnauthorizedException() {
    super("User unauthorized.");
  }
}

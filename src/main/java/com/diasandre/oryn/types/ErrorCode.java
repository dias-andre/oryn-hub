package com.diasandre.oryn.types;

import lombok.Getter;

@Getter
public enum ErrorCode {
  INVITE_LIMIT_EXCEEDED("This invite has reached its usage limit"),
  INVITE_EXPIRED("This invite has expired."),
  INVITE_PAUSED("This invite is paused"),
  USER_ALREADY_IN_SQUAD("This user is already member of this squad"),
  INSUFFICIENT_FUNDS("Insufficient funds"),

  INVALID_END_DATE("End date must be a future date"),
  INVALID_START_DATE("Start date must be a past date"),

  INVALID_EMAIL("Each user must have a valid email address"),
  EMAIL_EXISTS("E-mail already exists"),
  UNDEFINED("No error code");

  private final String defaultMessage;

  ErrorCode(String defaultMessage) {
    this.defaultMessage = defaultMessage;
  }
}
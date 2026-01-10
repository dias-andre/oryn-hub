package com.httpsdre.ragnarok.types;

import lombok.Getter;

@Getter
public enum ErrorCode {
  INVITE_LIMIT_EXCEEDED("This invite has reached its usage limit"),
  INVITE_EXPIRED("This invite has expired."),
  INVITE_PAUSED("This invite is paused"),
  USER_ALREADY_IN_SQUAD("This user is already member of this squad"),
  INSUFFICIENT_FUNDS("Insufficient funds"),
  UNDEFINED("No error code");

  private final String defaultMessage;

  ErrorCode(String defaultMessage) {
    this.defaultMessage = defaultMessage;
  }
}
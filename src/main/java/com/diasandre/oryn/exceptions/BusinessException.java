package com.diasandre.oryn.exceptions;

import com.diasandre.oryn.types.ErrorCode;
import lombok.Getter;


@Getter
public class BusinessException extends RuntimeException {
  private ErrorCode code = ErrorCode.UNDEFINED;
  public BusinessException(String message) {
    super(message);
  }
  public BusinessException() {
    super("Validation exception!");
  }
  public BusinessException(ErrorCode code) {
    super(code.getDefaultMessage());
    this.code = code;
  }
}

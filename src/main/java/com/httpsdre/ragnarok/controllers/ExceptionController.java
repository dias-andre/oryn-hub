package com.httpsdre.ragnarok.controllers;

import com.httpsdre.ragnarok.dtos.ErrorDTO;
import com.httpsdre.ragnarok.exceptions.NotFoundException;
import com.httpsdre.ragnarok.exceptions.UnauthorizedException;
import com.httpsdre.ragnarok.exceptions.UnprocessableEntityException;
import com.httpsdre.ragnarok.exceptions.BusinessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.OffsetDateTime;

@RestControllerAdvice
public class ExceptionController {

  @ExceptionHandler(UnauthorizedException.class)
  public ResponseEntity<ErrorDTO> unauthorizedException(UnauthorizedException e) {
    return ResponseEntity.status(401).body(new ErrorDTO("UNAUTHORIZED", e.getMessage(), OffsetDateTime.now()));
  }

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<ErrorDTO> notFoundException(NotFoundException e) {
    return ResponseEntity.status(404).body(new ErrorDTO("RESOURCE_NOT_FOUND", e.getMessage(), OffsetDateTime.now()));
  }

  @ExceptionHandler(UnprocessableEntityException.class)
  public ResponseEntity<ErrorDTO> unprocessableEntity(UnprocessableEntityException e) {
    return ResponseEntity.status(422).body(new ErrorDTO("UNPROCESSABLE_ENTITY", e.getMessage(), OffsetDateTime.now()));
  }

  @ExceptionHandler(BusinessException.class)
  public ResponseEntity<ErrorDTO> businessException(BusinessException e) {
    String code = (e.getCode() != null) ? e.getCode().name() : "BUSINESS_ERROR";
    return ResponseEntity.badRequest().body(new ErrorDTO(code, e.getMessage(), OffsetDateTime.now()));
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorDTO> genericException(Exception e) {
    e.printStackTrace();
    return ResponseEntity.status(500).body(
            new ErrorDTO("INTERNAL_SERVER_ERROR", "Internal server error", OffsetDateTime.now())
    );
  }
}

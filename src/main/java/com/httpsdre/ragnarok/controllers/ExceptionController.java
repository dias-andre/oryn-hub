package com.httpsdre.ragnarok.controllers;

import com.httpsdre.ragnarok.dtos.ErrorDTO;
import com.httpsdre.ragnarok.exceptions.NotFoundException;
import com.httpsdre.ragnarok.exceptions.UnauthorizedException;
import com.httpsdre.ragnarok.exceptions.UnprocessableEntityException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ExceptionController {
  @ExceptionHandler(UnauthorizedException.class)
  public ResponseEntity<ErrorDTO> unauthorizedException(UnauthorizedException e) {
    return ResponseEntity.status(401).body(new ErrorDTO(e.getMessage(), LocalDateTime.now()));
  }

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<ErrorDTO> notFoundException(NotFoundException e) {
    return ResponseEntity.status(404).body(new ErrorDTO(e.getMessage(), LocalDateTime.now()));
  }

  @ExceptionHandler(UnprocessableEntityException.class)
  public ResponseEntity<ErrorDTO> unprocessableEntity(UnprocessableEntityException e) {
    return ResponseEntity.status(422).body(new ErrorDTO(e.getMessage(), LocalDateTime.now()));
  }
}

package com.diasandre.oryn.controllers;

import com.diasandre.oryn.dtos.ErrorDTO;
import com.diasandre.oryn.exceptions.NotFoundException;
import com.diasandre.oryn.exceptions.UnauthorizedException;
import com.diasandre.oryn.exceptions.UnprocessableEntityException;
import com.diasandre.oryn.exceptions.BusinessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

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

  @ExceptionHandler(NoResourceFoundException.class)
  public ResponseEntity<ErrorDTO> noResourceFoundException(NoResourceFoundException e) {
    return ResponseEntity.badRequest().body(new ErrorDTO(
            "ROUTE_NOT_FOUND",
            e.getMessage(),
            OffsetDateTime.now()
    ));
  }
}

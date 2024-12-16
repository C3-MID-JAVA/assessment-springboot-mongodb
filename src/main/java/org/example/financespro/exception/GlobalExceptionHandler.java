package org.example.financespro.exception;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/** Centralized exception handler for the application. */
@RestControllerAdvice
public class GlobalExceptionHandler {

  /**
   * Handles CustomException and returns a 400 BAD REQUEST response.
   *
   * @param ex the CustomException instance
   * @return a ResponseEntity with error details
   */
  @ExceptionHandler(CustomException.class)
  public ResponseEntity<Map<String, String>> handleCustomException(CustomException ex) {
    Map<String, String> errorResponse = new HashMap<>();
    errorResponse.put("error", ex.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
  }

  /**
   * Handles general exceptions and returns a 500 INTERNAL SERVER ERROR response.
   *
   * @param ex the Exception instance
   * @return a ResponseEntity with error details
   */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<Map<String, String>> handleGeneralException(Exception ex) {
    Map<String, String> errorResponse = new HashMap<>();
    errorResponse.put("error", "An unexpected error occurred: " + ex.getMessage());
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, String>> handleValidationExceptions(
      MethodArgumentNotValidException ex) {
    Map<String, String> errors = new HashMap<>();
    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
      errors.put(error.getField(), error.getDefaultMessage());
    }
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
  }
}

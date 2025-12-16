package com.prodapt.licensetracker.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<String> handleBusinessException(
            BusinessException ex) {

        return ResponseEntity
                .badRequest()
                .body(ex.getMessage());
    }

    // optional: catch-all
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(
            Exception ex) {

        return ResponseEntity
                .status(500)
                .body("Internal Server Error");
    }
}

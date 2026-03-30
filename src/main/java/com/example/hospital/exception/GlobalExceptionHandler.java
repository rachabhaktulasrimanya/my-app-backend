package com.example.hospital.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleRuntime(RuntimeException ex) {

        return ResponseEntity.badRequest().body(
                Map.of(
                        "timestamp", LocalDateTime.now(),
                        "error", ex.getMessage()
                )
        );
    }
}
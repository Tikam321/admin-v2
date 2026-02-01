package com.tikam.simple_admin_v2.exception;

import com.tikam.simple_admin_v2.dto.APIResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AdminException.class)
    public ResponseEntity<APIResponse<Void>> handleAdminException(AdminException ex) {
        log.error("AdminException: {}", ex.getMessage());
        APIResponse<Void> response = APIResponse.<Void>builder()
                .success(false)
                .message(ex.getMessage())
                .data(null)
                .build();
        return new ResponseEntity<>(response, ex.getErrorCode().getStatus());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<APIResponse<Void>> handleResourceNotFoundException(ResourceNotFoundException ex) {
        log.error("ResourceNotFoundException: {}", ex.getMessage());
        APIResponse<Void> response = APIResponse.<Void>builder()
                .success(false)
                .message(ex.getMessage())
                .data(null)
                .build();
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<APIResponse<Map<String, String>>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        log.error("Validation error: {}", ex.getMessage());
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        System.out.println(ex.getBindingResult().getAllErrors());

        APIResponse<Map<String, String>> response = APIResponse.<Map<String, String>>builder()
                .success(false)
                .message("Validation Failed")
                .data(errors)
                .build();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<APIResponse<Void>> handleGlobalException(Exception ex) {
        log.error("Unexpected error: ", ex);
        APIResponse<Void> response = APIResponse.<Void>builder()
                .success(false)
                .message("An unexpected error occurred")
                .data(null)
                .build();
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

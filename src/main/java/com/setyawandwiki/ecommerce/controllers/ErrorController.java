package com.setyawandwiki.ecommerce.controllers;

import com.setyawandwiki.ecommerce.dto.WebResponse;
import jakarta.servlet.ServletException;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

@RequiredArgsConstructor
@RestControllerAdvice
public class ErrorController {
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<WebResponse<String>> constraintValidationException(ConstraintViolationException exception){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(WebResponse.<String>builder().errors(exception.getMessage()).build());
    }
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<WebResponse<String>> runtimeException(RuntimeException exception){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(WebResponse.<String>builder().errors(exception.getMessage()).build());
    }
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<WebResponse<String>> responseStatusException(ResponseStatusException exception){
        return ResponseEntity.status(exception.getStatusCode()).body(WebResponse.<String>builder().errors(exception.getMessage()).build());
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<WebResponse<String>> exceptionHandler(Exception exception){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(WebResponse.<String>builder().errors(exception.getMessage()).build());
    }
    @ExceptionHandler(ServletException.class)
    public ResponseEntity<WebResponse<String>> servletExceptionException(ServletException exception){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(WebResponse.<String>builder().errors(exception.getMessage()).build());
    }
    @ExceptionHandler(IOException.class)
    public ResponseEntity<WebResponse<String>> ioException(IOException exception){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(WebResponse.<String>builder().errors(exception.getMessage()).build());
    }
}

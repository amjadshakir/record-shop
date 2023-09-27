package com.techreturners.recordshop.exceptionhandler;

import com.techreturners.recordshop.exception.InvalidRecordInputException;
import com.techreturners.recordshop.exception.RecordAlreadyExistsException;
import com.techreturners.recordshop.exception.RecordNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(RecordNotFoundException.class)
    public ResponseEntity<Object> handleRecordNotFoundException(RecordNotFoundException exception, WebRequest request) {
        // Handle the exception and return an appropriate HTTP response
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = RecordAlreadyExistsException.class)
    public ResponseEntity handleRecordAlreadyExistsException(
            RecordAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(value = InvalidRecordInputException.class)
    public ResponseEntity handleInvalidRecordInputException(
            InvalidRecordInputException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}
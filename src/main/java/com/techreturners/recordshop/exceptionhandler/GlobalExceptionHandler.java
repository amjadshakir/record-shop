package com.techreturners.recordshop.exceptionhandler;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    public static final String GENRE = "genre";
    public static final String RELEASE_YEAR = "releaseYear";
    public static final String STOCK = "stock";

    @ExceptionHandler(value = HttpMessageNotReadableException.class)

    public ResponseEntity<String> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        if (ex.getRootCause() instanceof InvalidFormatException) {
            InvalidFormatException invalidFormatEx =
                    (InvalidFormatException) ex.getRootCause();

            String fieldName = invalidFormatEx.getPath().get(0).getFieldName();
            String message = switch (fieldName) {
                case GENRE -> "Invalid value for enum Music Genre: " +
                        invalidFormatEx.getValue() +
                        " Please enter valid value";
                case RELEASE_YEAR -> "Invalid value for Release Year: " +

                        invalidFormatEx.getValue() +
                        " Please enter valid value for year in format YYYY";
                case STOCK -> "Invalid value for Stock: " +
                        invalidFormatEx.getValue() +
                        " Please enter valid positive integer value for stock";
                default -> "";
            };
            return ResponseEntity.badRequest().body(message);
        }
        return ResponseEntity.badRequest().body("Bad Request");
    }
}

package com.techreturners.recordshop.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = HttpMessageNotReadableException.class)

    public ResponseEntity<String> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        if (ex.getRootCause() instanceof InvalidFormatException) {
            InvalidFormatException invalidFormatEx =
                    (InvalidFormatException) ex.getRootCause();

            String fieldName = invalidFormatEx.getPath().get(0).getFieldName();
            String message="";
            if (fieldName.equals("genre"))
               message = "Invalid value for enum Music Genre: "+
                       invalidFormatEx.getValue()+
                       " Please enter valid value";
            else if (fieldName.equals("releaseYear"))
                message = "Invalid value for Release Year: "+
                        invalidFormatEx.getValue()+
                        " Please enter valid value for year in format YYYY";
            else if (fieldName.equals("stock"))
                message = "Invalid value for Stock: "+
                        invalidFormatEx.getValue()+
                        " Please enter valid positive integer value for stock";
            return ResponseEntity.badRequest().body(message);
        }
        return ResponseEntity.badRequest().body("Bad Request");
    }
}

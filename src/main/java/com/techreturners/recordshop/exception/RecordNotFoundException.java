package com.techreturners.recordshop.exception;

public class RecordNotFoundException extends RuntimeException {
    String message;
    public RecordNotFoundException(){}
    public RecordNotFoundException(String message){
        super(message);
        this.message = message;
    }
}

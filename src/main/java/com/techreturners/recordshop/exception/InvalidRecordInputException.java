package com.techreturners.recordshop.exception;

public class InvalidRecordInputException extends RuntimeException{
    private String message;
    public InvalidRecordInputException(){}
    public InvalidRecordInputException(String message){
        super(message);
        this.message = message;
    }
}

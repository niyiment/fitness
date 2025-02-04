package com.niyiment.fitness.exception;


public class ResourceNotFoundException extends  RuntimeException {

    public ResourceNotFoundException() {
        super("Record not found");
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }
}

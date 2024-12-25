package com.cpg.bim.exception;

public class ReviewAlreadyExistsException extends RuntimeException {
    private String message;
    public ReviewAlreadyExistsException(String message) {
        super(message);  
        this.message = message;
    }
    public String getMessage() {
        return message;  
    }
    @Override
    public String toString() {
        return "ReviewAlreadyExistsException: " + message;
    }
}
package com.cpg.bim.exception;

public class UserNotFoundException extends RuntimeException {
    private String message;
    public UserNotFoundException(String message) {
        super(message);  
        this.message = message;
    }
    public String getMessage() {
        return message;  
    }
    @Override
    public String toString() {
        return "UserNotFoundException: " + message;
    }
}
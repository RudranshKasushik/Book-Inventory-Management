package com.cpg.bim.exception;

public class BookconditionNotFoundException extends RuntimeException {
	 
    public BookconditionNotFoundException(String message) {
        super(message);
    }
 
    public BookconditionNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
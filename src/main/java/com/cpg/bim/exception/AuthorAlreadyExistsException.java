package com.cpg.bim.exception;

public class AuthorAlreadyExistsException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;
    public AuthorAlreadyExistsException(String message) {
        super(message);
        this.message=message;
    }
    public String getMessage() {
        return message;
    }

    
}

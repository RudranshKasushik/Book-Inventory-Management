package com.cpg.bim.exception;
 
public class PermroleNotFoundException extends RuntimeException {
	public PermroleNotFoundException(String message) {
        super(message);
    }
 
    public PermroleNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
 
}
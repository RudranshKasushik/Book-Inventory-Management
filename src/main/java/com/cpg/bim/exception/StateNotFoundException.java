package com.cpg.bim.exception;

import org.springframework.http.HttpStatus;

@SuppressWarnings("serial")
public class StateNotFoundException extends RuntimeException {
    private static final String CODE = "NOTFOUND";
	@SuppressWarnings("unused")
	private HttpStatus status;

    public StateNotFoundException(String message) {
        super(message);
    }
    
    public StateNotFoundException(String message,HttpStatus status) {
        super(message);
        this.status = status;
    }
    public String getCode() {
        return CODE;
    }
}

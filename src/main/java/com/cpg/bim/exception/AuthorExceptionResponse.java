package com.cpg.bim.exception;

public class AuthorExceptionResponse {
	private String code;
    private String message;
    
    public AuthorExceptionResponse(String code,String message) {
        this.code=code;
        this.message = message;
    }
 
    public void setCode(String code) {
		this.code = code;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getCode() {
        return code;
    }
 
    public String getMessage() {
        return message;
    }
}
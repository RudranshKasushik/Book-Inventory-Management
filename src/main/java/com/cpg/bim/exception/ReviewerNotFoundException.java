package com.cpg.bim.exception;

import org.springframework.http.HttpStatus;

@SuppressWarnings("serial")
public class ReviewerNotFoundException extends RuntimeException{
	
	 private String code= "NOTFOUND";
	@SuppressWarnings("unused")
	private HttpStatus status;
	public ReviewerNotFoundException(String message) {
	        super(message);
	    }
	 public ReviewerNotFoundException(String code,HttpStatus status) {
	        this.code=code;
	        this.status = status;
	    }
	 public String getCode() {
		 return code;
	 }
	 
	}



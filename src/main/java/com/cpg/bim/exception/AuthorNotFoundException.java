package com.cpg.bim.exception;

import org.springframework.http.HttpStatus;

public class AuthorNotFoundException extends RuntimeException{
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String code= "NOTFOUND";
		@SuppressWarnings("unused")
		private HttpStatus status;

	 public AuthorNotFoundException(String message) {
	        super(message);
	        }
	 public AuthorNotFoundException(String code,HttpStatus status) {
	        this.code=code;
	        this.status = status;
	    }
	 public String getCode() {
		 return code;
	 }
}
	 
	
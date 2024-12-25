package com.cpg.bim.exception;


public class BookNotFoundException extends RuntimeException{

	private String message;
	
	public BookNotFoundException() {}

	public BookNotFoundException(String message) {
		this.message = message;
	}
	

	public String getMessage() {
		return message;
	}
	
	public String toString() {
		return "com.rud.bpp.exception.BookNotFound :" + message;
	}
}

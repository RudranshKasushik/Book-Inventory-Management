package com.cpg.bim.exception;

public class PublisherNotFoundException extends RuntimeException{

	private String message;
	
	public PublisherNotFoundException() {}

	public PublisherNotFoundException(String message) {
		this.message = message;
	}
	

	public String getMessage() {
		return message;
	}
	
	public String toString() {
		return "com.rud.bpp.exception.PublisherNotFound :" + message;
	}
}

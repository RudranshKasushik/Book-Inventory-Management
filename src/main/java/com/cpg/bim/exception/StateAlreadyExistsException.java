package com.cpg.bim.exception;


@SuppressWarnings("serial")
public class StateAlreadyExistsException extends RuntimeException {
    private String message;
    public StateAlreadyExistsException(String message) {
    	super(message);
        this.message = message;
    }  

		public void setMessage(String message) {
			this.message = message;
		}

		
        public String getMessage() {
            return message;
        }
    }
     

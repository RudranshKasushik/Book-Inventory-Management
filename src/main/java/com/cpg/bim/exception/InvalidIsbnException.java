package com.cpg.bim.exception;

public class InvalidIsbnException extends RuntimeException
{
    public InvalidIsbnException(String message) {
        super(message);
    }
}
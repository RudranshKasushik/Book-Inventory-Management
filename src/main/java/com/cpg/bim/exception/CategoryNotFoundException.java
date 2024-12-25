package com.cpg.bim.exception;

public class CategoryNotFoundException extends RuntimeException
{
    public CategoryNotFoundException(String message)
    {
        super(message); // Just passing the message
    }
}
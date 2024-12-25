package com.cpg.bim.exception;

public class ShoppingCartAlreadyExistsException extends RuntimeException
{
    public ShoppingCartAlreadyExistsException(String message) {
        super(message);
    }
}
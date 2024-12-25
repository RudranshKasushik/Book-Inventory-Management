package com.cpg.bim.exception;

public class ShoppingCartNotFoundException extends RuntimeException
{
    public ShoppingCartNotFoundException(String message) {
        super(message);
    }
}
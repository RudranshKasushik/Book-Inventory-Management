package com.cpg.bim.exception;

public class InventoryAlreadyTakenException extends RuntimeException
{
    public InventoryAlreadyTakenException(String message)
    {
        super(message);
    }
}
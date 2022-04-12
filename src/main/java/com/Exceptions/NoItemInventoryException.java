package com.Exceptions;

public class NoItemInventoryException extends Exception {
    public NoItemInventoryException(String msg)
    {
        super(msg);
    }
    NoItemInventoryException(String msg, Throwable cause)
    {
        super(msg,cause);
    }
}

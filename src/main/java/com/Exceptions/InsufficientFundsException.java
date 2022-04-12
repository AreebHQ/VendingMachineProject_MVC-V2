package com.Exceptions;

public class InsufficientFundsException extends Exception {
   public InsufficientFundsException(String msg)
    {
        super(msg);
    }
    public InsufficientFundsException(String msg, Throwable cause)
    {
        super(msg,cause);
    }
}

package com.Exceptions;

public class VendingMachineDuplicateItemException extends Exception{
    public VendingMachineDuplicateItemException(String msg)
    {
        super(msg);
    }
    public VendingMachineDuplicateItemException(String msg, Throwable cause)
    {
        super(msg,cause);
    }
}

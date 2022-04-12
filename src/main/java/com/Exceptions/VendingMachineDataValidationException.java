package com.Exceptions;

public class VendingMachineDataValidationException extends Exception {
    public VendingMachineDataValidationException(String msg)
    {
        super(msg);
    }
    public VendingMachineDataValidationException(String msg, Throwable cause)
    {
        super(msg,cause);
    }
}

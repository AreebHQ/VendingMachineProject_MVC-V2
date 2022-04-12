package com.Exceptions;

public class VendingMachineInventoryPersistenceException extends Exception {
    public VendingMachineInventoryPersistenceException(String message)
    {
        //calling parent class i.e: Exception constructor
        super(message);
    }

    public VendingMachineInventoryPersistenceException(String message, Throwable cause)
    {
        //calling parent class i.e: Exception constructor
        super(message,cause);
    }
}

package com.Dao;

import com.Exceptions.VendingMachineInventoryPersistenceException;

public interface VendingMachineAuditDao {
    void writeAuditEntry(String entry) throws VendingMachineInventoryPersistenceException;
}

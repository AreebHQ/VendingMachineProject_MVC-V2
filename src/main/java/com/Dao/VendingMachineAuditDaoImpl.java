package com.Dao;

import com.Exceptions.VendingMachineInventoryPersistenceException;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

@Component
public class VendingMachineAuditDaoImpl implements VendingMachineAuditDao{

    public static final String AUDIT_FILE = "inventory_audit.txt";

    @Override
    public void writeAuditEntry(String entry) throws VendingMachineInventoryPersistenceException {
        PrintWriter out;

        try {
            out = new PrintWriter(new FileWriter(AUDIT_FILE, true));
        } catch (IOException e) {
            throw new VendingMachineInventoryPersistenceException("Could not persist audit information.", e);
        }

        LocalDateTime timestamp = LocalDateTime.now();
        out.println(timestamp + " : " + entry);
        out.flush();
    }
}

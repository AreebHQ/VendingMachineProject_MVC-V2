package com.Service;

import com.Dao.VendingMachineAuditDao;
import com.Exceptions.VendingMachineInventoryPersistenceException;
import org.springframework.stereotype.Component;

@Component
public class VendingMachineAuditDaoStubImpl implements VendingMachineAuditDao {

    @Override
    public void writeAuditEntry(String entry) throws VendingMachineInventoryPersistenceException {
    }
}

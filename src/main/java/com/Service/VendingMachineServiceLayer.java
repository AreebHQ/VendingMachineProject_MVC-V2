package com.Service;

import com.Dto.Item;
import com.Exceptions.*;

import java.util.List;

public interface VendingMachineServiceLayer {
    Item addItem(Item item) throws VendingMachineDaoException, VendingMachineInventoryPersistenceException, VendingMachineDataValidationException, VendingMachineDuplicateItemException;
    List<Item> getAllItems();
    Item getItem(String name) throws VendingMachineDataValidationException;
    Item removeItem(String itemName) throws VendingMachineInventoryPersistenceException, VendingMachineDaoException;
    void updateItem(Item item) throws VendingMachineDaoException, VendingMachineInventoryPersistenceException;
    List<String> buyItem(double amount, String itemCode) throws VendingMachineDataValidationException, NoItemInventoryException, VendingMachineDaoException, InsufficientFundsException, VendingMachineInventoryPersistenceException;
}

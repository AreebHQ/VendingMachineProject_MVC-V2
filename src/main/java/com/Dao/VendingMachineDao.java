package com.Dao;

import com.Dto.Item;
import com.Exceptions.*;

import java.util.List;

public interface VendingMachineDao {
    Item addItem(Item item) throws VendingMachineDaoException, VendingMachineDuplicateItemException;
    List<Item> getAllItems();
    Item getItem(String name) throws VendingMachineDataValidationException;
    Item removeItem(String itemName) throws VendingMachineDaoException;
    void updateItem() throws VendingMachineDaoException;
    List<String> buyItem(double amount, String itemCode) throws NoItemInventoryException, InsufficientFundsException, VendingMachineDaoException;

}

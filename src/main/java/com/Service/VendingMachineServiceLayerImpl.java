package com.Service;

import com.Dao.*;
import com.Dto.Item;
import com.Exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class VendingMachineServiceLayerImpl implements VendingMachineServiceLayer{


    VendingMachineDao dao;
    VendingMachineAuditDao auditDao;

    @Autowired
    public VendingMachineServiceLayerImpl(VendingMachineDao dao, VendingMachineAuditDao auditDao)
    {
        this.dao = dao;
        this.auditDao = auditDao;
    }

    private void validateItemData(Item item) throws VendingMachineDataValidationException {
        if(item.getName().trim().isEmpty() || item.getPrice() <= 0 || item.getQuantity() < 0 )
        {
            throw new VendingMachineDataValidationException("Error! All fields are required!");
        }
    }

    @Override
    public Item addItem(Item item) throws VendingMachineDaoException, VendingMachineInventoryPersistenceException, VendingMachineDataValidationException, VendingMachineDuplicateItemException {

       List<Item> allItems = dao.getAllItems();
        if(allItems != null) {
            for (Item check : allItems) {
                if (check.getName().equals(item.getName()) || check.getCode().equals(item.getCode())) {
                    throw new VendingMachineDuplicateItemException("Could not add item. Item " + item.getName() + " already exist!");
                }
            }
        }
        validateItemData(item);
        Item newItem = dao.addItem(item);
        auditDao.writeAuditEntry("Inventory item " + item.getName() + " Added");
        return newItem;
    }

    @Override
    public List<Item> getAllItems() {
        return dao.getAllItems();
    }

    @Override
    public Item getItem(String name) throws VendingMachineDataValidationException {
        return dao.getItem(name);
    }

    @Override
    public Item removeItem(String itemName) throws VendingMachineInventoryPersistenceException, VendingMachineDaoException {
        Item removedItem = dao.removeItem(itemName);
        if(removedItem != null)
        {auditDao.writeAuditEntry("Inventory item " + itemName + " Removed");}
        return removedItem;
    }

    @Override
    public void updateItem(Item item) throws VendingMachineDaoException, VendingMachineInventoryPersistenceException {
       dao.updateItem();
       auditDao.writeAuditEntry("Inventory item " + item.getName() + " Updated");
    }

    @Override
    public List<String> buyItem(double amount, String itemCode) throws VendingMachineDataValidationException, NoItemInventoryException, VendingMachineDaoException, InsufficientFundsException, VendingMachineInventoryPersistenceException {

       if(dao.getItem(itemCode) == null)
        {throw new VendingMachineDataValidationException("Item not found!");}

        Item item = dao.getItem(itemCode);
        if (item.getQuantity() > 0) {

            auditDao.writeAuditEntry("Inventory item " + item.getName() + " Sold");
            List<String> change = dao.buyItem(amount,itemCode);
            return change;

        } else
            { throw new NoItemInventoryException("Item out of stock!");}

    }


}

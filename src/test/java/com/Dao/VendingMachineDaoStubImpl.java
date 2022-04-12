package com.Dao;

import com.Dto.Item;
import com.Exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class VendingMachineDaoStubImpl implements VendingMachineDao{
    public Item onlyItem;
    List<Item> itemList = new ArrayList<>();

    @Autowired
    public VendingMachineDaoStubImpl(){

        onlyItem = new Item();
        onlyItem.setCode("ite");
        onlyItem.setName("TestItemOne");
        onlyItem.setPrice(2.50);
        onlyItem.setQuantity(10);
    }

    @Autowired
    public VendingMachineDaoStubImpl (Item testItem) {
        this.onlyItem = testItem;

    }


    @Override
    public Item addItem(Item item) throws VendingMachineDuplicateItemException {

       if(item.getCode().equals(onlyItem.getCode()))
       {
           throw new VendingMachineDuplicateItemException("");
       } else {
           itemList.add(item);
           return item;
       }
    }

    @Override
    public List<Item> getAllItems() {

        return itemList;
    }

    @Override
    public Item getItem(String itemCode) throws VendingMachineDataValidationException {
        if(itemCode.equals(onlyItem.getCode()))
        {
            return onlyItem;
        } else {
           for(Item i : itemList)
           {
               if(i.getCode().equals(itemCode))
               {
                   return i;
               }
           }

        }
        return null;
    }

    @Override
    public Item removeItem(String itemCode) throws VendingMachineDaoException {
        if(itemCode.equals(onlyItem.getCode()))
        {
            return onlyItem;
        } for(Item i : itemList)
        {
            if(i.getCode().equals(itemCode))
            {
                Item x = i;
                itemList.remove(i);
                return x;
            }
        }
        return null;
    }

    @Override
    public void updateItem() throws VendingMachineDaoException {

    }

    @Override
    public List<String> buyItem(double amount, String itemCode) throws NoItemInventoryException, InsufficientFundsException, VendingMachineDaoException {
       // Item item = items.get(itemCode);
        List<String> change = new ArrayList<>();
        if(amount < onlyItem.getPrice())
        {
            return null;
        }
        else {
            change.add("amount is good");
        }
        if (onlyItem.getQuantity() > 0)
        {
            onlyItem.setQuantity(onlyItem.getQuantity() - 1);
            change.add("quantity is good");
        } else {
            return null;
        }
        return change;

    }
}

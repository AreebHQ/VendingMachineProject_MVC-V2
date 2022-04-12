package com.Dao;

import com.Dto.Item;
import com.Exceptions.InsufficientFundsException;
import com.Exceptions.NoItemInventoryException;
import com.Exceptions.VendingMachineDaoException;
import com.Exceptions.VendingMachineDataValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Component
public class VendingMachineDaoImpl implements VendingMachineDao {

    public final String INVENTORY_FILE;
    public static final String DELIMITER = "::";
    Map<String,Item> items = new HashMap<>();


    @Autowired
    public VendingMachineDaoImpl() throws VendingMachineDaoException {
        this.INVENTORY_FILE = "inventory.txt";
        loadInventory();
    }

    @Autowired
    public VendingMachineDaoImpl(String inventoryTextFile) throws VendingMachineDaoException {
        this.INVENTORY_FILE = inventoryTextFile;
       // loadInventory();
    }

    @Override
    public Item addItem(Item item) throws VendingMachineDaoException {

        //validation in service layer
        item.setCode(getItemCode());
        Item newItem = items.put(item.getCode(),item);
        writeInventory();
        return newItem;
    }

    @Override
    public List<Item> getAllItems() {
       return new ArrayList<>(items.values());

    }

    @Override
    public Item getItem(String itemCode) throws VendingMachineDataValidationException {
        return items.get(itemCode);
    }

    @Override
    public Item removeItem(String itemCode) throws VendingMachineDaoException {
       Item item = items.remove(itemCode);
       writeInventory();
       return item;
    }

    @Override
    public void updateItem() throws VendingMachineDaoException {
       writeInventory();
    }

    @Override
    public List<String> buyItem(double amount, String itemCode) throws NoItemInventoryException, InsufficientFundsException, VendingMachineDaoException {

        Item item = items.get(itemCode);
        List<String> change = processPayment(amount,itemCode);
        //when item is bought, reduce the quantity
        item.setQuantity(item.getQuantity() - 1);
        writeInventory();
        return change;
    }

    //generate item code for the item
    private String getItemCode()
    {
        //random alphanumeric string
        String randomStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuilder code;
        do {
            code = new StringBuilder(3);
            for (int i = 0; i < 3; i++)
            {
                code.append(randomStr.charAt(random.nextInt(randomStr.length())));
            }
        } while (isExist(code.toString()));

        return code.toString();
    }

    //check if item code already exist in inventory
    private boolean isExist(String code)
    {
        return items.containsKey(code);
    }


    public List<String> processPayment(double amount, String itemCode) throws InsufficientFundsException {

        List<String> changeList = new ArrayList<>();
        //getting item price in pennies
        BigDecimal itemPrice = new BigDecimal(String.valueOf(items.get(itemCode).getPrice()*100));
        //getting user money amount in pennies
        BigDecimal userMoney = new BigDecimal(String.valueOf((amount*100)));

        if (userMoney.doubleValue() - itemPrice.doubleValue() < 0) {
            throw new InsufficientFundsException("Insufficient Amount, " + amount);
        }

        String pennies = userMoney.subtract(itemPrice).setScale(2, RoundingMode.HALF_UP).toBigInteger().toString();
        //getting change from change class
        Change change = new Change(Integer.parseInt(pennies));
        String changeInCoin = Change.getChangeInCoins();
        String changeInDollar = Change.getChangeInDollars();
        changeList.add(changeInCoin);
        changeList.add(changeInDollar);
        return changeList;

    }


    private String marshallItem(Item item)
    {
        String itemAsText = item.getCode()+DELIMITER;
        itemAsText+=item.getName()+DELIMITER;
        itemAsText+=item.getPrice()+DELIMITER;
        itemAsText+=item.getQuantity();
        return itemAsText;
    }

    private Item unmarshallItem(String itemAsText)
    {
        String []itemToken = itemAsText.split(DELIMITER);
        Item itemFromFile = new Item();
        itemFromFile.setCode(itemToken[0]);
        itemFromFile.setName(itemToken[1]);
        itemFromFile.setPrice(Double.parseDouble(itemToken[2]));
        itemFromFile.setQuantity(Integer.parseInt(itemToken[3]));
        return itemFromFile;
    }

    private void writeInventory() throws VendingMachineDaoException
    {
        PrintWriter out;

        try {
            out = new PrintWriter(new FileWriter(INVENTORY_FILE));
        } catch (IOException e) {
            throw new VendingMachineDaoException("Could not save Inventory data", e);
        }

        String itemAsText;
        List<Item> itemsList = new ArrayList<>(items.values());

        for(Item item : itemsList)
        {
            itemAsText = marshallItem(item);
            out.println(itemAsText);
            out.flush();
        }

        out.close();
    }

    private void loadInventory() throws VendingMachineDaoException {
        Scanner scanner;

        try {
            scanner = new Scanner(new BufferedReader(new FileReader(INVENTORY_FILE)));
        } catch (FileNotFoundException e) {
            throw new VendingMachineDaoException("Could not load inventory data", e);
        }

        String currentLine;
        Item currentItem;

        while (scanner.hasNextLine())
        {
            currentLine = scanner.nextLine();
            if(!currentLine.isEmpty())
            {
                currentItem = unmarshallItem(currentLine);
                items.put(currentItem.getCode(), currentItem);
            }
        }

        scanner.close();
    }
}

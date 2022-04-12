package com.UI;

import com.Dto.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VendingMachineView {

    @Autowired
    UserIO io;

    @Autowired
    public VendingMachineView (UserIO io) {
        this.io = io;
    }

    public int printMenuAndGetSelection() {
        io.print("Main Menu");
        io.print("1. View Items Details");
        io.print("2. Buy Item");
        io.print("3. Add Inventory");
        io.print("4. Remove Inventory");
        io.print("5. Update Inventory");
        io.print("6. Exit");

        return io.readInt("Please select from the above choices:",1,6);

    }

    public Item getNewItemInfo(){

        String itemName = io.readString("Please enter Item Name:");
        String price = io.readString("Please enter item's price:");
        String quantity = io.readString("Please enter inventory level - quantity:");

        Item item = new Item();
        item.setName(itemName);
        item.setPrice(Double.parseDouble(price));
        item.setQuantity(Integer.parseInt(quantity));

        return item;
    }

    public void displayItemDetails(List<Item> itemsList)
    {
        for(Item item : itemsList)
        {
            String itemInfo = String.format("#%s : %s \t\t $%.2f \t\t Qty: %s",
                    item.getCode(),
                    item.getName(),
                    item.getPrice(),
                    item.getQuantity());
            io.print(itemInfo);
        }
        io.readString("Please hit enter to continue.");
    }
    public void displayItemList(List<Item> itemsList)
    {
        if(itemsList != null) {

            //using Lambda function
            itemsList.stream().forEach((item -> {
                String itemInfo;
                if (item.getQuantity() == 0) { itemInfo = String.format("#%s : %s $%.2f %s",
                        item.getCode(),
                        item.getName(),
                        item.getPrice(),
                        "Sold Out!");
                } else {
                    itemInfo = String.format("#%s : %s \t$%.2f",
                            item.getCode(),
                            item.getName(),
                            item.getPrice()); }
                io.print(itemInfo);
            }));
        } else {
        io.print("No Items found");
    }

    }

    public Item updateItemInfo(Item item)
    {
        String itemName = io.readString("Please enter Item Name:");
        String price = io.readString("Please enter item's price:");
        String quantity = io.readString("Please enter inventory level - quantity:");
        if(itemName.isEmpty()){itemName = item.getName();}
        if(price.isEmpty()){price = String.valueOf(item.getPrice());}
        if(quantity.isEmpty()){quantity = String.valueOf(item.getQuantity());}

        item.setName(itemName);
        item.setPrice(Double.parseDouble(price));
        item.setQuantity(Integer.parseInt(quantity));

        return item;
    }

    public void displayRemoveResult(Item item) {
        if(item != null){
            io.print("Item successfully removed.");
        }else{
            io.print("No such item found. Try with full item name");
        }
        io.readString("Please hit enter to continue.");
    }

    public void displayUpdateResult(Item item) {
        if(item != null){
            io.print("Item successfully Updated.");
        }else{
            io.print("No such item found. Try with full item name");
        }
        io.readString("Please hit enter to continue.");
    }

    public void displayBoughtItemResult(Item item) {
        if(item != null){
            displayBuySuccessBanner();
            io.print("Item "+ item.getName() + " successfully bought.");
        }else{
            io.print("No such item found. Try with full item name");
        }
        io.print("\n");
    }

    public void displayChange(List<String> change){
        String totalChange = String.format("%.2f",Double.parseDouble(change.get(1)));
        io.print("Your total change is $" +totalChange );
        io.print("Machine will return these coins: \n" + change.get(0) + "\n");
        io.readString("Please hit enter to continue.");
    }

    //Get User Choices
    public String getItemCodeChoice() { return io.readString("Please enter Item code:");}
    public double getMoneyInput(){return io.readDouble("Please enter money $:");}

    // Display Banners
    public void displayAddItemBanner(){io.print("===========||Add Item||===========");}
    public void displayAddSuccessBanner(){io.print("===========||Success! Item Added||===========");}
    public void displayBuySuccessBanner(){io.print("===========||Success! Item Bought||===========");}
    public void displayAllItemsBanner() { io.print("=== All Items ===");}
    public void displayUpdateItemBanner () { io.print("=== Update Item ==="); }
    public void displayRemoveItemBanner () { io.print("=== Remove Item ==="); }
    public void displayEnterItemCodeBanner(){io.print("===========||Enter Item Code||===========");}
    public void displayEnterMoneyBanner(){io.print("===========||Enter Money in $||===========");}
    public void displayExitBanner() { io.print("Good Bye!!!");}
    public void displayUnknownCommandBanner() { io.print("Unknown Command!!!"); }
    public void displayErrorBanner (String msg) { io.print("=== Error ==="); io.print(msg); }

}

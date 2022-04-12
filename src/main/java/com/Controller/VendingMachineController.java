package com.Controller;

import com.Dto.Item;
import com.Exceptions.*;
import com.Service.VendingMachineServiceLayer;
import com.UI.VendingMachineView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.util.List;

@Component
public class VendingMachineController {


    private final VendingMachineView view;

    private final VendingMachineServiceLayer service;

    @Autowired
    public VendingMachineController (VendingMachineServiceLayer service, VendingMachineView view)
    {
        this.service = service;
        this.view = view;
    }

    public void run() {

        boolean keepGoing = true;
        int menuSelection = 0;
        double amount = 0.0;
        try {
            while (keepGoing) {
                viewItems();
                menuSelection = getMenuSelection();

                switch (menuSelection) {
                    case 1:
                        viewItemsDetail();
                        break;
                    case 2:
                        view.displayEnterMoneyBanner();
                        //get money input
                        amount = view.getMoneyInput();
                        buyItems(amount);
                        break;
                    case 3:
                        addItems();
                        break;
                    case 4:
                        removeItem();
                        break;
                    case 5:
                        updateItem();
                        break;
                    case 6:
                        keepGoing = false;
                        break;
                    default:
                        unknownCommand();

                } // end switch
            }// end while
            view.displayExitBanner();

        } catch (VendingMachineDaoException e) {
            view.displayErrorBanner("Could not save Inventory data");
        } catch (NoItemInventoryException e) {
            view.displayErrorBanner("Item out of stock!");
        } catch (InsufficientFundsException e) {
            view.displayErrorBanner("Insufficient amount! $" + amount);
        } catch (VendingMachineDataValidationException e) {
            view.displayErrorBanner("Item not found");
        } catch (VendingMachineInventoryPersistenceException e) {
            view.displayErrorBanner("Error reading/writing audit data");
        } catch (VendingMachineDuplicateItemException e) {
            view.displayErrorBanner("Could not add item. Item already exists!");
        }
    }

    private void buyItems(double amount) throws NoItemInventoryException, InsufficientFundsException, VendingMachineDaoException, VendingMachineDataValidationException, VendingMachineInventoryPersistenceException {

        //get item code the user wants to buy
        view.displayEnterItemCodeBanner();
        String itemCode = view.getItemCodeChoice().toUpperCase();
        Item item = service.getItem(itemCode);
        //process payment //buy item
        List<String> itemChange = service.buyItem(amount,itemCode);
        //display item bought
        view.displayBoughtItemResult(item);
        //display remaining change
        view.displayChange(itemChange);

    }

    private void updateItem() throws VendingMachineDaoException, VendingMachineDataValidationException, VendingMachineInventoryPersistenceException {
       view.displayUpdateItemBanner();
       String itemCode = view.getItemCodeChoice().toUpperCase();
       Item item = service.getItem(itemCode);
       view.updateItemInfo(item);
       service.updateItem(item);
       view.displayUpdateResult(item);

    }

    private void removeItem() throws VendingMachineDaoException, VendingMachineInventoryPersistenceException {
        view.displayRemoveItemBanner();
        String itemCode = view.getItemCodeChoice().toUpperCase();
        Item item = service.removeItem(itemCode);
        view.displayRemoveResult(item);
    }


    private void addItems() throws VendingMachineDaoException, VendingMachineInventoryPersistenceException, VendingMachineDuplicateItemException, VendingMachineDataValidationException {
        view.displayAddItemBanner();
        Item item = view.getNewItemInfo();
        service.addItem(item);
        view.displayAddSuccessBanner();
    }

    private void viewItemsDetail() {
        view.displayAllItemsBanner();
        List<Item> itemList = service.getAllItems();
        view.displayItemDetails(itemList);
    }

    private void viewItems() {
        view.displayAllItemsBanner();
        List<Item> itemList = service.getAllItems();
        view.displayItemList(itemList);
    }
    private int getMenuSelection() {
        return view.printMenuAndGetSelection();
    }

    private void unknownCommand() {
        view.displayUnknownCommandBanner();
    }

}

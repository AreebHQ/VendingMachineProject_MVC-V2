package com.Dao;

import com.Dto.Item;
import com.Exceptions.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Component
class VendingMachineDaoImplTest {

    VendingMachineDao testDao;

    @Autowired
    public VendingMachineDaoImplTest() throws IOException {

    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() throws Exception {
        String testFile = "testInventory.txt";
        new FileWriter(testFile);
        testDao = new VendingMachineDaoImpl(testFile);
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testSomeMethod() {
        //fail("The test case is a prototype.");
    }


    @Test
    public void testAddGetItem() throws Exception {
       //Arrange
       Item item = new Item();
       item.setName("TestPepsi");
       item.setPrice(3.50);
       item.setQuantity(15);
        //Act
       testDao.addItem(item);
       Item retrieveItem = testDao.getItem(item.getCode());
       //Assert
        assertEquals(item.getCode(),retrieveItem.getCode(),"check code");
        assertEquals(item.getName(),retrieveItem.getName(),"check name");
        assertEquals(item.getPrice(),retrieveItem.getPrice(),"check price");
        assertEquals(item.getQuantity(),retrieveItem.getQuantity(),"check quantity");

    }

    @Test
    public void testAddGetAllItem() throws Exception {

        //Arrange
        Item item = new Item();
        item.setName("TestPepsi1");
        item.setPrice(3.50);
        item.setQuantity(15);
        //Arrange
        Item item2 = new Item();
        item.setName("TestCoke2");
        item.setPrice(1.50);
        item.setQuantity(5);
        //Act: add both to dao
        testDao.addItem(item);
        testDao.addItem(item2);
        List<Item> allItems = testDao.getAllItems();
        //Assert
        assertNotNull(allItems,"List not Null");
        assertEquals(2,allItems.size(),"we added two items");
        assertTrue(allItems.contains(item),"list include TestPepsi1");
        assertTrue(allItems.contains(item2),"list include TestCoke2");
    }


    @Test
    public void testAddRemoveItem() throws Exception {
        //Arrange
        Item item = new Item();
        item.setName("TestPepsi1");
        item.setPrice(3.50);
        item.setQuantity(15);
        //Arrange
        Item item2 = new Item();
        item.setName("TestCoke2");
        item.setPrice(1.50);
        item.setQuantity(5);
        //Act: add both to dao
        testDao.addItem(item);
        testDao.addItem(item2);

        Item removedItem = testDao.removeItem(item.getCode());

        assertEquals(removedItem,item,"removed TestPepsi1");
        List<Item> allItems = testDao.getAllItems();
        //Assert
        assertNotNull(allItems,"Still one item");
        assertEquals(1,allItems.size(),"one=1");
        assertFalse(allItems.contains(item),"TestPepsi1 is gone");
        assertTrue(allItems.contains(item2),"TestCoke2 is here");
        //Act
        removedItem = testDao.removeItem(item2.getCode());
        //Assert again
        assertEquals(removedItem,item2,"removed TestCoke2");
        allItems = testDao.getAllItems();
        assertTrue(allItems.isEmpty());
        Item getItem = testDao.getItem(item.getCode());
        assertNull(getItem);
        Item getItem2 = testDao.getItem(item2.getCode());
        assertNull(getItem2);

    }

    @Test
    public void testBuyItem() throws Exception {
        //Arrange
        Item item = new Item();
        item.setName("TestPepsi1");
        item.setPrice(3.50);
        item.setQuantity(15);

        testDao.addItem(item);

        List<String> change = testDao.buyItem(4.50,item.getCode());
        assertEquals(item.getQuantity(),14,"bought 1=one");
        assertNotNull(change,"got change in return");

        try {
            List<String> change2 = testDao.buyItem(2.50,item.getCode());
            fail("This was a wrong Exception. Do better!!");
        } catch (InsufficientFundsException e) {
            return;
        }
    }


}
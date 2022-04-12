package com.Service;

import com.Dto.Item;
import com.Exceptions.VendingMachineDaoException;
import com.Exceptions.VendingMachineDataValidationException;
import com.Exceptions.VendingMachineDuplicateItemException;
import com.Exceptions.VendingMachineInventoryPersistenceException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import static org.junit.jupiter.api.Assertions.*;

@Component
public class VendingMachineServiceLayerImplTest {

    @Autowired
   private final VendingMachineServiceLayer testService;

    @Autowired
    public VendingMachineServiceLayerImplTest(){
       /* VendingMachineDao dao = new VendingMachineDaoStubImpl();
        VendingMachineAuditDao auditDao = new VendingMachineAuditDaoStubImpl();
        testService = new VendingMachineServiceLayerImpl(dao,auditDao);*/



       ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContextTest.xml");
        testService = ctx.getBean("serviceLayer", VendingMachineServiceLayer.class);

        /*AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext();
        appContext.scan("test");
        appContext.refresh();

        testService = appContext.getBean("service",VendingMachineServiceLayer.class);*/

    }


    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testValidItem() {
        Item coke = new Item();
        coke.setCode("fan");
        coke.setName("TestFanta");
        coke.setPrice(2.50);
        coke.setQuantity(10);

        try {
            testService.addItem(coke);
        } catch (VendingMachineDaoException | VendingMachineInventoryPersistenceException | VendingMachineDataValidationException | VendingMachineDuplicateItemException e) {
           fail("this was a validity test", e);
        }
    }

    @Test
    public void testDupItem() {

        Item coke = new Item();
        coke.setCode("ite");
        coke.setName("TestItemOne");
        coke.setPrice(2.50);
        coke.setQuantity(10);

        try {
            testService.addItem(coke);
            fail("This was the Duplicate Test!!");
        } catch (VendingMachineDaoException e) {
            fail("This was a wrong Exception. Do better!!");
        } catch (VendingMachineInventoryPersistenceException e) {
            fail("This was a wrong Exception. Do better!!");
        } catch (VendingMachineDataValidationException e) {
            fail("This was a wrong Exception. Do better!!");
        } catch (VendingMachineDuplicateItemException e) {
           return;
        }
    }

    @Test
    public void testInvalidItem() {
        Item coke = new Item();
        coke.setCode("noo");
        coke.setName("");
        coke.setPrice(2.50);
        coke.setQuantity(10);

        try {
            testService.addItem(coke);
            fail("added without checking - fail!!");
        } catch (VendingMachineInventoryPersistenceException | VendingMachineDuplicateItemException | VendingMachineDaoException e) {
            fail("This was a wrong Exception. Do better!!");
        }  catch (VendingMachineDataValidationException e) {
           return;
        }
    }

    @Test
    public void testGetAllItems() throws Exception {
        Item onlyitem = new Item();
        onlyitem.setCode("two");
        onlyitem.setName("TestOnlyTwo");
        onlyitem.setPrice(9.99);
        onlyitem.setQuantity(9);

        testService.addItem(onlyitem);

        assertEquals(1,testService.getAllItems().size(),"only 1 item - is 1 length");
        assertTrue(testService.getAllItems().contains(onlyitem));
    }

    @Test
    public void testGetItems() throws Exception {

        Item onlyitem = new Item();
        onlyitem.setCode("thr");
        onlyitem.setName("TestOnyThree");
        onlyitem.setPrice(9.99);
        onlyitem.setQuantity(9);

        testService.addItem(onlyitem);

        //Item one = testService.getItem("aaa");
        Item one = testService.getItem("thr");
        assertNotNull(one);
        assertEquals(onlyitem,one);

        Item notInDaq = testService.getItem("ghg");
        assertNull(notInDaq);

    }
    @Test
    public void testRemoveItem() throws Exception {

        Item onlyitem = new Item();
        onlyitem.setCode("fou");
        onlyitem.setName("TestOnlyFour");
        onlyitem.setPrice(9.99);
        onlyitem.setQuantity(9);
        testService.addItem(onlyitem);
        Item one = testService.removeItem("fou");
        assertNotNull(one);
        assertEquals(onlyitem,one);

        Item notInDao = testService.removeItem("546");
        assertNull(notInDao);

    }

}

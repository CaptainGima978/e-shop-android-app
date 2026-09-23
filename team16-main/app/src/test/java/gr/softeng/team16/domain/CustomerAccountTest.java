package gr.softeng.team16.domain;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;

public class CustomerAccountTest {

    private CustomerAccount customerAccount;

    private Address address;
    private Order order;
    private Card card1;
    private Card card2;

    private Card card1Duplicate;

    private Customer_Build customerBuildpublished;
    private Customer_Build customerBuildunpublished;


    @Before
    public void setUp() throws Exception {
        address = new Address("Athens", "Main St", 123, "12345");
        customerAccount = new CustomerAccount("John Doe", "johndoe", "johndoe@xample.com", address);

        customerAccount.Customer_Builds_liked = new HashSet<>();
        customerAccount.Customer_Builds_saved = new HashSet<>();

        card1 = new Card("1234567890123456", 123, "12/25");
        card2 = new Card("6543210987654321", 456, "11/24");

        card1Duplicate = new Card("1234567890123456", 999, "01/30");


        order = new Order(new ArrayList<>(), customerAccount, address);

        customerBuildpublished = new Customer_Build("Test Published Build");
        customerBuildunpublished = new Customer_Build("Test Unpublished Build");

        customerBuildpublished.setPublished(true);
    }

    @Test
    public void addCard_True_whenNewCard() {
        assertTrue(customerAccount.addCard(card1));
        assertEquals(1, customerAccount.getCardList().size());

    }

    @Test
    public void addCard_False_whenDuplicateCard() {
        customerAccount.addCard(card1);
        assertFalse(customerAccount.addCard(card1Duplicate));
        assertEquals(1, customerAccount.getCardList().size());
    }


    @Test
    public void addOrderToList() {
        customerAccount.addOrder(order);
        assertEquals(1, customerAccount.getOrders().size());
        assertTrue(customerAccount.getOrders().contains(order));
    }

    @Test
    public void removeCard() {
        customerAccount.addCard(card1);
        customerAccount.addCard(card2);

        customerAccount.removeCard(card1);

        assertEquals(1, customerAccount.getCardList().size());
        assertFalse(customerAccount.getCardList().contains(card1));
        assertTrue(customerAccount.getCardList().contains(card2));
    }



    @Test
    public void addCustomer_Build_liked_checkPublish() {

        customerAccount.addCustomer_Build_liked(customerBuildpublished);
        assertTrue(customerAccount.Customer_Builds_liked.contains(customerBuildpublished));

        customerAccount.addCustomer_Build_liked(customerBuildunpublished);
        assertFalse(customerAccount.Customer_Builds_liked.contains(customerBuildunpublished));

        assertEquals(1, customerAccount.Customer_Builds_liked.size());

    }

    @Test
    public void addCustomer_Build_saved_checkPublish() {
        customerAccount.addCustomer_Build_saved(customerBuildpublished);
        assertTrue(customerAccount.Customer_Builds_saved.contains(customerBuildpublished));

        customerAccount.addCustomer_Build_saved(customerBuildunpublished);
        assertFalse(customerAccount.Customer_Builds_saved.contains(customerBuildunpublished));

        assertEquals(1, customerAccount.Customer_Builds_saved.size());
    }


    @Test
    public void removeCustomer_Build_liked() {
        customerAccount.addCustomer_Build_liked(customerBuildpublished);
        customerAccount.removeCustomer_Build_liked(customerBuildpublished);
        assertTrue(customerAccount.Customer_Builds_liked.isEmpty());
    }


    @Test
    public void removeCustomer_Build_saved() {
        customerAccount.addCustomer_Build_saved(customerBuildpublished);
        customerAccount.removeCustomer_Build_saved(customerBuildpublished);
        assertTrue(customerAccount.Customer_Builds_saved.isEmpty());
    }
}
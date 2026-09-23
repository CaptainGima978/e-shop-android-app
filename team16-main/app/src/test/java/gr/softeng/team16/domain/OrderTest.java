package gr.softeng.team16.domain;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class OrderTest {
    private Order order;
    private Cart cart;
    private OrderLine orderLine1;

    private OrderLine orderLine2;

    private CustomerAccount c1;

    private Address addr;

    private Card card;
    private Card card2;

    @Before
    public void setUp() throws Exception {
        cart = new Cart();
        Product p1 = new Product(40001, "RTX 4090", 239.90); //GPU
        Product p2 = new Product(30000, "AMD Ryzen 7", 450.50); //CPU
        orderLine1 = new OrderLine(p1);
        orderLine2 = new OrderLine(p2);
        cart.addToCart(orderLine1);
        cart.addToCart(orderLine2);
        addr = new Address("Petroupoli", "Valtetsiou",25,"13232");
        c1 = new CustomerAccount("Vaggelis", "Vag1", "vagvag@gmail.com", addr);
        order = new Order(cart.getProductList(),c1, addr);
        card = new Card("12345", 678, "06/26");
        card2 = new Card ("34356", 789, "04/25");
    }

    @Test
    public void processPayment_whenCardNull() {
        order.processPayment(null); //The card is null
        assertEquals("PAYMENT FAILED", order.getStatus());

    }

    @Test
    public void processPayment_CardFails_expired() {
        order.processPayment(card2);
        assertEquals("PAYMENT FAILED", order.getStatus());

    }
    @Test
    public void processPayment_CardPasses() {
        order.processPayment(card);
        assertEquals("PENDING ORDER", order.getStatus());

    }

}
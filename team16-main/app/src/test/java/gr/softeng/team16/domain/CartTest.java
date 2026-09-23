package gr.softeng.team16.domain;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class CartTest {
    private Cart cart;
    private OrderLine orderLine1;
    private OrderLine orderLine2;

    private OrderLine orderLine3;

    private OrderLine orderLine4;

    @Before
    public void setUp() throws Exception {
        cart = new Cart();
        Product p1 = new Product(40000, "RTX 5060", 339.90); //GPU
        Product p2 = new Product(40001, "RTX 4090", 239.90); //GPU
        Product p3 = new Product(30000, "AMD Ryzen 7", 450.50); //CPU
        orderLine1 = new OrderLine(p1);
        orderLine2 = new OrderLine(p1);
        orderLine3 = new OrderLine(p2);
        orderLine4 = new OrderLine(3,p3);
    }

    @Test
    public void addToCart_newProduct() {
        cart.addToCart(orderLine1);
        assertFalse(cart.getProductList().isEmpty());
       assertEquals(orderLine1.getProduct(),cart.getProductList().get(0).getProduct()); //we make sure that the product that just got added is the same as the orderLine one
    }

    @Test
    public void addToCart_existingProduct(){
        cart.addToCart(orderLine1);
        cart.addToCart(orderLine2); //same product added
        assertEquals(2,cart.getProductList().get(0).getQuantity());
    }

    @Test
    public void addToCart_differentProduct(){
        cart.addToCart(orderLine1);
        cart.addToCart(orderLine2);
        cart.addToCart(orderLine3); //different product added (GPU but different model)
        cart.addToCart(orderLine4); //different product added (CPU, a completely different product)
        assertNotEquals(3,cart.getProductList().get(0).getQuantity());
        assertEquals(1,cart.getProductList().get(1).getQuantity());
        assertEquals(3,cart.getProductList().get(2).getQuantity());
    }

    @Test
    public void removeFromCart_existingProduct(){
        cart.addToCart(orderLine1);
        cart.addToCart(orderLine4); // the cart should have 2 different products
        assertEquals(4, cart.getNumOfProducts());
        assertTrue(cart.removeFromCart(orderLine4));
    }

    @Test
    public void removeFromCart_NotExistingProduct(){
        cart.addToCart(orderLine1);
        cart.addToCart(orderLine4); // the cart should have 2 different products, with quantities of 1 and 3, 4 products in total
        assertFalse(cart.removeFromCart(orderLine3));
    }

    @Test
    public void increaseQuantity() {
        cart.addToCart(orderLine3);
        cart.addToCart(orderLine4);
        assertEquals(4, cart.getNumOfProducts()); //there should be 4 products in the cart in total before the increase (2 orderlines of 3 and 1 products)
        cart.increaseQuantity(orderLine4);
        assertEquals(5, cart.getNumOfProducts()); //there should be 5 products in the cart in total after the increase (2 orderlines of 4 and 1 products)
    }

    @Test
    public void decreaseQuantity() {
        cart.addToCart(orderLine4);
        assertEquals(3, cart.getNumOfProducts()); //there should be 3 products in the cart in total before the decrease (1 orderline of 3 products)
        cart.decreaseQuantity(orderLine4);
        assertEquals(2, cart.getNumOfProducts()); //there should be 2 products in the cart after the decrease (1 orderline of 2 products)
    }

    @Test
    public void getTotalPrice() {
       cart.addToCart(orderLine1);
       cart.addToCart(orderLine2);
       cart.addToCart(orderLine3);
       cart.addToCart(orderLine4);
       assertEquals(2271.2, cart.getTotalPrice(), 0.01);
    }

}
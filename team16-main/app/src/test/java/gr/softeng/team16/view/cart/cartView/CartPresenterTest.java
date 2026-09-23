package gr.softeng.team16.view.cart.cartView;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import gr.softeng.team16.domain.Address;
import gr.softeng.team16.domain.OrderLine;
import gr.softeng.team16.domain.Product;
import gr.softeng.team16.util.LocalRepository;

public class CartPresenterTest {

    private CartPresenter presenter;
    private CartViewStub viewStub;
    private LocalRepository repo;

    @Before
    public void setUp() throws Exception {
        viewStub = new CartViewStub();
        repo = new LocalRepository();
        presenter = new CartPresenter(repo);
        presenter.setView(viewStub);
    }

    @Test(expected = NullPointerException.class)
    public void load_null_pointer_vulnerability_check() {
        presenter.setRepo(null);
        presenter.loadCart();
    }

    @Test
    public void testLoadCartSuccess_withProduct() {
        Product test_Product = new Product(1, "Test product", 100.0);
        OrderLine test_line = new OrderLine(test_Product);
        List<OrderLine> products = new ArrayList<>();
        products.add(test_line);
        repo.setSuccessful(true);
        repo.setCartToReturn(products);

        presenter.loadCart();

        assertTrue(viewStub.ShowCartProducts());
        assertTrue(presenter.getCart().getProductList().size() > 0);
    }

    @Test
    public void testLoadCartEmpty() {
        repo.setSuccessful(true);
        repo.setCartToReturn(new ArrayList<>());

        presenter.loadCart();

        assertTrue(viewStub.ShowCartProducts());
        assertTrue("Cart is empty", presenter.getCart().getProductList().isEmpty());
    }

    @Test
    public void testLoadCartFailure() {
        repo.setSuccessful(false);

        presenter.loadCart();

        assertFalse(viewStub.ShowCartProducts());
        assertTrue(viewStub.getErrorCount() > 0);
    }

    @Test
    public void addProductToCart_repo_Works_newOrderline() {
        Product test_Product = new Product(1, "Test product", 100.0);
        OrderLine firstLine = new OrderLine(test_Product);

        presenter.addToCart(firstLine);

        assertEquals(1, repo.getAddedProducts().size());
    }

    @Test
    public void addProductToCart_repo_Works_WithExistingOrderline() {
        Product test_Product = new Product(1, "Test product", 100.0);
        OrderLine firstLine = new OrderLine(test_Product);
        OrderLine secondLine = new OrderLine(test_Product);

        presenter.addToCart(firstLine);
        presenter.addToCart(secondLine);


        assertEquals(2, repo.getAddedProducts().size());
    }

    @Test
    public void addProductToCart_repo_Works_WithNullOrderline() {
        presenter.addToCart(null);
        assertTrue(repo.getAddedProducts().isEmpty());
    }

    @Test
    public void increaseQuantity_Success() {
        Product testProduct = new Product(1, "Test product", 100.0);
        OrderLine testLine = new OrderLine(testProduct);
        presenter.addToCart(testLine);

        presenter.increaseQuantity(0); //Position 0 is the first product in the cart


        assertEquals(2, presenter.getCart().getProductList().get(0).getQuantity());
        assertTrue(viewStub.ShowCartProducts());
        assertTrue(viewStub.isTotalPriceUpdated()); //We make sure that the view.showCart() and view.updateTotalPrice() are called
    }

    @Test
    public void decreaseQuantity_Success() {
        Product testProduct = new Product(1, "Test product", 100.0);
        OrderLine testLine = new OrderLine(2, testProduct);
        presenter.getCart().addToCart(testLine);

        presenter.decreaseQuantity(0);

        assertEquals(1, presenter.getCart().getProductList().get(0).getQuantity());
        assertTrue(viewStub.ShowCartProducts());
        assertTrue(viewStub.isTotalPriceUpdated()); //We make sure that the view.showCart() and view.updateTotalPrice() are called
    }

    @Test
    public void removeFromCart_Success() {
        Product testProduct = new Product(1, "Test product", 100.0);
        OrderLine testLine = new OrderLine(testProduct);
        presenter.addToCart(testLine);

        presenter.removeFromCart(0);

        assertTrue(presenter.getCart().getProductList().isEmpty());
        assertTrue(viewStub.ShowCartProducts());
        assertTrue(viewStub.isTotalPriceUpdated()); //We make sure that the view.showCart() and view.updateTotalPrice() are called
    }

    @Test
    public void clearCart_Success() {
        Product testProduct = new Product(1, "Test product", 100.0);
        presenter.addToCart(new OrderLine(testProduct));

        presenter.clearCart();

        assertTrue(repo.getAddedProducts().isEmpty());
    }

    @Test
    public void placeOrder_ExistingAddress() {
        Product testProduct = new Product(1, "Test product", 100.0);
        presenter.addToCart(new OrderLine(testProduct));
        Address address = new Address("Athens", "Street", 1, "13232");

        presenter.placeOrder(address);

        assertTrue(repo.getNumOfOrders() == 1); //We make sure that the order was placed
        assertEquals("Order placed successfully!", viewStub.getSuccessMessage()); //We make sure that the view.showSuccess() is called with the correct message
    }

    @Test
    public void placeOrder_NullAddress() {
        presenter.placeOrder(null);

        assertTrue(repo.getNumOfOrders() == 0);
    }

    //Note: The CartPresenter methods "not covered" are setters/getters
}

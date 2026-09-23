package gr.softeng.team16.view.cart.cartView;

import java.util.List;

import gr.softeng.team16.data.DataRepository;
import gr.softeng.team16.data.FirebaseRepository;
import gr.softeng.team16.data.LoadCallback;
import gr.softeng.team16.domain.Address;
import gr.softeng.team16.domain.Cart;
import gr.softeng.team16.domain.Order;
import gr.softeng.team16.domain.OrderLine;
import com.google.firebase.database.DatabaseError;

public class CartPresenter {

    private static CartPresenter instance;
    private Cart cart;
    private CartView view;

    private DataRepository repo;

    public CartPresenter(DataRepository repo) {
        this.cart = new Cart();
        this.repo = repo;
    }

    public CartPresenter() {
        this.cart = new Cart();
    }

    public static CartPresenter getInstance() {
        if (instance == null) {
            instance = new CartPresenter();
        }
        return instance;
    }

    public static void setInstance(CartPresenter presenter) {
        instance = presenter;
    }

    public void setView(CartView view) {
        this.view = view;
    }

    public void loadCart() {
        repo.getCart(new LoadCallback<List<OrderLine>>() {
            @Override
            public void onLoaded(List<OrderLine> result) {
                cart = new Cart();
                for (OrderLine line : result) {
                    cart.addToCart(line);
                }
                if (view != null) {
                    view.showCart();
                    view.updateTotalPrice();
                }
            }

            @Override
            public void onError(DatabaseError error) {
                if (view != null) {

                    String message = (error != null) ? error.getMessage() : "Unknown database error";
                    view.showError(message);
                }
            }
        });
    }

    public boolean addToCart(OrderLine orderLine) {
        if (orderLine == null) return false;
        for (OrderLine line : cart.getProductList()) {
            if (line.equals(orderLine)) {
                increaseQuantity(cart.getProductList().indexOf(orderLine));
                return true;
            }
        }
        cart.addToCart(orderLine);
        repo.addProductToCart(orderLine);
        return true;
    }

    public void increaseQuantity(int position) {
        OrderLine line = cart.getProductList().get(position);
        cart.increaseQuantity(line);
        // Sync with DB if needed
        repo.addProductToCart(line);
        if (view != null) {
            view.showCart();
            view.updateTotalPrice();
        }
    }

    public void decreaseQuantity(int position) {
        OrderLine line = cart.getProductList().get(position);
        cart.decreaseQuantity(line);
        repo.addProductToCart(line);
        if (view != null) {
            view.showCart();
            view.updateTotalPrice();
        }
    }

    public void removeFromCart(int position) {
        OrderLine line = cart.getProductList().get(position);
        cart.removeFromCart(line);
        repo.removeProductFromCart(line);
        if (view != null) {
            view.showCart();
            view.updateTotalPrice();
        }
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public void setRepo(DataRepository repo) {
        this.repo = repo;
    }

    public DataRepository getRepo() {
        return repo;
    }

    public void clearCart() {
        cart.clearCart();
        repo.clearUserCart(cart);

    }

    public void placeOrder(Address billingAddress) {
        if (billingAddress == null) return;
        Order order = new Order(cart.getNumOfProducts(), cart.getTotalPrice());
        order.setLines(cart.getProductList());
        order.setAddress(billingAddress);
        order.setStatus("PENDING ORDER");
        repo.addOrder(order);
        clearCart();
        loadCart();
        if (view != null) {
            view.showSuccess("Order placed successfully!");
        }
    }
}

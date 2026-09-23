package gr.softeng.team16.domain;
import java.util.ArrayList;
import java.util.List;

public class Cart {

    private static Cart instance;
    private double totalPrice;
    private int numOfProducts;
    private List<OrderLine> productList;

    public Cart() {
        this.totalPrice = 0.0;
        this.numOfProducts = 0;
        this.productList = new ArrayList<>();
    }

    /**
     * Adds an OrderLine to the cart. If the product already exists in the cart,
     * it updates the quantity and total price accordingly.
     *
     * @param orderLine The OrderLine to be added to the cart.
     */
    public void addToCart(OrderLine orderLine) {
        Product product = orderLine.getProduct();
        int quantity = orderLine.getQuantity();

        for (OrderLine line : productList) {
            if (product.getId() == line.getProduct().getId()) {
                line.setQuantity(line.getQuantity() + quantity);
                totalPrice += product.getPrice() * quantity;
                numOfProducts += quantity;
                return;
            }
        }
        productList.add(orderLine);
        totalPrice += product.getPrice() * quantity;
        numOfProducts += quantity;
    }

    /**
     * Clears the cart by removing all products and resetting total price and product count.
     */
    public void clearCart() {
        productList.clear();
        totalPrice = 0.0;
        numOfProducts = 0;
    }

    /**
     * Increases the quantity of a specific OrderLine in the cart by one.
     *
     * @param line The OrderLine whose quantity is to be increased.
     */
    public void increaseQuantity(OrderLine line) {
        if (productList.contains(line)) {
            int currentQuantity = line.getQuantity();
            line.setQuantity(currentQuantity + 1);
            totalPrice += line.getProduct().getPrice();
            numOfProducts += 1;
        }
    }

    /**
     * Decreases the quantity of a specific OrderLine in the cart by one.
     * If the quantity reaches zero, the OrderLine is removed from the cart.
     *
     * @param line The OrderLine whose quantity is to be decreased.
     */
    public void decreaseQuantity(OrderLine line) {
        if (productList.contains(line)) {
            int currentQuantity = line.getQuantity();
            if (currentQuantity > 0) {
                line.setQuantity(currentQuantity - 1);
                totalPrice -= line.getProduct().getPrice();
                numOfProducts -= 1;
                if (line.getQuantity() == 0) {
                    productList.remove(line);
                }
            }
        }
    }

    /**
     * Removes a specific OrderLine from the cart entirely.
     *
     * @param orderLine The OrderLine to be removed from the cart.
     */
    public boolean removeFromCart(OrderLine orderLine) {
        if (productList.contains(orderLine)) {
            totalPrice -= orderLine.getProduct().getPrice() * orderLine.getQuantity();
            numOfProducts -= orderLine.getQuantity();
            productList.remove(orderLine);
            return true;
        }
        return false;
    }


    public List<OrderLine> getProductList() {
        return productList;
    }
    public void setQuantityList(List<OrderLine> productList) {
        this.productList = productList;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public int getNumOfProducts() {
        return numOfProducts;
    }

}

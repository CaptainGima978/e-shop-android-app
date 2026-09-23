package gr.softeng.team16.view.cart.cartView;

public interface CartView {

    /**
     * Updates the total price of the cart.
     */
    void updateTotalPrice();

    /**
     * Shows the current products of the cart.
     */
    void showCart();

    /**
     * Shows a success message.
     * @param message The error message to display.
     */
    void showSuccess(String message);

    /**
     * Shows an error message.
     * @param message The error message to display.
     */
    void showError(String message);
}

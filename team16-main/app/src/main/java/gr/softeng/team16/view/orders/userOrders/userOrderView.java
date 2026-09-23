package gr.softeng.team16.view.orders.userOrders;

import java.util.List;
import gr.softeng.team16.domain.Order;

public interface userOrderView {
    /**
     * Shows the user's orders
     */
    void showOrders(List<Order> orders);

    /**
     * Shows the number of orders the user has made
     */
    void showNoOrders();

    /**
     * Navigates back to the previous page
     */
    void navigateBack();

    /**
     * Displays success message to the user
     */
    void showSuccess(String message);

    /**
     * Displays error message to the user
     */
    void showError(String message);
}

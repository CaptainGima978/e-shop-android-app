package gr.softeng.team16.view.orders.adminOrders;

import androidx.lifecycle.AndroidViewModel;

import java.util.List;

import gr.softeng.team16.domain.Order;

public interface AdminOrdersView{

    /**
     * Shows the list of orders in the UI.
     * @param result The list of orders to be displayed.
     */
    void showOrders(List<Order> result);

    /**
     * Shows an error message to the user.
     * @param message The error message to be displayed.
     */

    void showError(String message);
}

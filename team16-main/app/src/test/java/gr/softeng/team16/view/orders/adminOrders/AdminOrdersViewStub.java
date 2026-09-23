package gr.softeng.team16.view.orders.adminOrders;

import java.util.List;

import gr.softeng.team16.domain.Order;

public class AdminOrdersViewStub implements AdminOrdersView {
    private List<Order> orders;
    private String errorMessage;
    private int showOrdersCalls = 0;
    private int showErrorCalls = 0;

    @Override
    public void showOrders(List<Order> result) {
        this.orders = result;
        showOrdersCalls++;
    }

    @Override
    public void showError(String message) {
        this.errorMessage = message;
        showErrorCalls++;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public int getShowOrdersCalls() {
        return showOrdersCalls;
    }

    public int getShowErrorCalls() {
        return showErrorCalls;
    }
}

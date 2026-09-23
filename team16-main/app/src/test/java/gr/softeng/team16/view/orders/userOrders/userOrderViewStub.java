package gr.softeng.team16.view.orders.userOrders;

import java.util.List;

import gr.softeng.team16.domain.Order;

public class userOrderViewStub implements  userOrderView{
    private List<Order> orders;
    private String errorMessage;
    private String successMessage;
    private int showOrdersCalls = 0;

    private boolean navigatedBack = false;

    public List<Order> getOrders() {
        return orders;
    }
    public int getShowOrdersCalls() {
        return showOrdersCalls;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
    public String getSuccessMessage() {
        return successMessage;
    }
    public boolean isNavigatedBack() {
        return navigatedBack;
    }
    @Override
    public void showOrders(List<Order> orders) {
        this.orders = orders;
        showOrdersCalls++;
    }

    @Override
    public void showNoOrders() {
        return;
    }

    @Override
    public void navigateBack() {
        navigatedBack = true;

    }

    @Override
    public void showSuccess(String message) {
        successMessage = message;

    }

    @Override
    public void showError(String message) {
        errorMessage = message;
    }
}

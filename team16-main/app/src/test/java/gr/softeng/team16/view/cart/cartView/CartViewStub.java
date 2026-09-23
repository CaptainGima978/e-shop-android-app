package gr.softeng.team16.view.cart.cartView;

public class CartViewStub implements CartView {
    private int errorCount = 0;
    private String successMessage = "";

    private String errorMessage = "";

    private boolean totalPriceUpdated = false;

    private boolean showCartProducts = false;

    public int getErrorCount() {
        return errorCount;
    }

    public String getSuccessMessage() {
        return successMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public boolean isTotalPriceUpdated() {
        return totalPriceUpdated;
    }

    public boolean ShowCartProducts() {
        return showCartProducts;
    }

    @Override
    public void updateTotalPrice() {
        totalPriceUpdated = true;
    }

    @Override
    public void showCart() {
        showCartProducts = true;

    }

    @Override
    public void showSuccess(String message) {
        successMessage = message;

    }

    @Override
    public void showError(String message) {
        errorCount++;
        errorMessage = message;

    }
}

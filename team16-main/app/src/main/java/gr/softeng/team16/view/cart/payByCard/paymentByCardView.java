package gr.softeng.team16.view.cart.payByCard;

public interface paymentByCardView {
    /**
     * Shows the user if the card number  they typed is right or wrong.
     * @param correctNumber True if the card number is a 16-digit number without letters.
     */
    void setCardNumberValidity(boolean correctNumber);

    /**
     * Shows the user if the card holder name they typed is right or wrong.
     * @param correctName True if it is only letters and no numbers.
     */
    void setCardHolderValidity(boolean correctName);

    /**
     * Shows the user if the card expiration date they typed is right or wrong.
     * @param correctDate True if the date typed hasn't passed.
     */
    void setExpDateValidity(boolean correctDate);

    /**
     * Shows the user if the CVC number they typed is right or wrong.
     * @param correctCVC True if the CVC is a 3-digit number without letters.
     */
    void setCVCValidity(boolean correctCVC);

    /**
     * Shows a success message once the user confirms the payment and the order is placed to be accepted/rejected.
     * @param successmsg The success message to be shown
     */
    void showPaymentSuccess(String successmsg);

    /**
     * Navigates the user back to the previous page.
     */
    void navigateToPreviousPage();

    /**
     * Confirms the payment after making sure that no field has been left empty
     */
    void confirmPay();

    /**
     * Navigates to Home Screen for the user to buy other stuff
     */
    void returnToHomeScreen();


}

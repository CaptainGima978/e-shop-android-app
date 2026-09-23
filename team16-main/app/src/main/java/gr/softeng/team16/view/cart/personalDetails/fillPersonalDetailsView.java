package gr.softeng.team16.view.cart.personalDetails;

public interface fillPersonalDetailsView {

    /**
     * Shows the user if the name they typed is right or wrong.
     * @param correctName  True if it is only letters and no numbers.
     */
    void setNameValidity(boolean correctName);

    /**
     * Shows the user if the surname they typed is right or wrong.
     * @param correctSurname  True if it is only letters and no numbers.
     */
    void setSurnameValidity(boolean correctSurname);

    /**
     * Shows the user if the address they typed is right or wrong.
     * @param correctAddress True if it consists of a street name (letters only), followed by 1-3 numbers (street address numbers) and there is a space between the street name and the street address numbers .
     */
    void setAddressValidity(boolean correctAddress);

    /**
     * Shows the user if the city name they typed is right or wrong.
     * @param correctCity  True if it is only letters and no numbers.
     */
    void setCityValidity(boolean correctCity);

    /**
     * Shows the user if the zipcode number they typed is right or wrong.
     * @param correctZipCode True if the zipcode is a 5-digit number without letters.
     */
    void setZipCodeValidity(boolean correctZipCode);

    /**
     * Shows the user if the email they typed is right or wrong.
     * @param correctEmail  True if it is the same email as the one the user has registered and made his account with.
     */
    void setEmailValidity(boolean correctEmail);

    /**
     * Confirms the personal details fill-in after making sure that no field has been left empty
     */
    void confirmContinue();

    /**
     * Navigates the user to the paymentByCard page.
     */
    void navigateToPayment();

    /**
     * Navigates the user back to home page.
     */
    void navigateToHomePage();

    void showErrorMessage(String message);
}

package gr.softeng.team16.view.cart.personalDetails;

public class fillPersonalDetailsViewStub implements fillPersonalDetailsView {
    private int errorCount = 0;
    private boolean isNameValid = true;
    private boolean isSurnameValid = true;
    private boolean isAddressValid = true;
    private boolean isCityValid = true;
    private boolean isZipCodeCorrect = true;
    private boolean navigatedToPayment = false;
    private boolean isEmailValid = true;
    private boolean navigatedToHomePage = false;

    private String errormessage = "";

    public boolean continueConfirmed = false;

    public int getErrorCount() {
        return errorCount;
    }

    public String getErrormessageMessage() {
        return errormessage;
    }

    public boolean isNameValid() {
        return isNameValid;
    }

    public boolean isSurnameValid() {
        return isSurnameValid;
    }

    public boolean isAddressValid() {
        return isAddressValid;
    }

    public boolean isCityValid() {
        return isCityValid;
    }

    public boolean isZipCodeCorrect() {
        return isZipCodeCorrect;
    }

    public boolean isEmailValid() {
        return isEmailValid;
    }
    public boolean isContinueConfirmed() {
        return continueConfirmed;
    }

    public boolean isNavigatedToPayment() {
        return navigatedToPayment;
    }

    public boolean isNavigatedToHomePage() {
        return navigatedToHomePage;
    }

    @Override
    public void setNameValidity(boolean correctName) {
        isNameValid = correctName;
        if(!correctName){
            errorCount++;
        }
    }

    @Override
    public void setSurnameValidity(boolean correctSurname) {
        isSurnameValid = correctSurname;
        if(!correctSurname){
            errorCount++;
        }

    }

    @Override
    public void setAddressValidity(boolean correctAddress) {
        isAddressValid = correctAddress;
        if(!correctAddress){
            errorCount++;
        }

    }

    @Override
    public void setCityValidity(boolean correctCity) {
        isCityValid = correctCity;
        if(!correctCity){
            errorCount++;
        }

    }

    @Override
    public void setZipCodeValidity(boolean correctZipCode) {
        isZipCodeCorrect = correctZipCode;
        if(!correctZipCode){
            errorCount++;
        }

    }

    @Override
    public void setEmailValidity(boolean correctEmail) {
        isEmailValid = correctEmail;
        if(!correctEmail){
            errorCount++;
        }

    }

    @Override
    public void confirmContinue() {
        continueConfirmed = true;
    }


    @Override
    public void navigateToPayment() {
        navigatedToPayment = true;
    }

    @Override
    public void navigateToHomePage() {
        navigatedToHomePage = true;
    }

    @Override
    public void showErrorMessage(String message) {
        errormessage = message;
        errorCount++;
    }
}

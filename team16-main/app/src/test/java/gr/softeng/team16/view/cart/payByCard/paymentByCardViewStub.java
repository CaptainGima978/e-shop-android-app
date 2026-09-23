package gr.softeng.team16.view.cart.payByCard;

public class paymentByCardViewStub implements  paymentByCardView{

    private int errorCount = 0;
    private boolean isCardNumberValid = true;
    private boolean isCardHolderValid = true;
    private boolean isExpDateValid = true;
    private boolean isCVCValid = true;
    private boolean navigatedToPreviousPage = false;

    private boolean navigatedToHomeScreen = false;

    private boolean PayConfirmed = false;
    private String errormessage = "";

    private String successMessage = "";

    public boolean isPayConfirmed() {
        return PayConfirmed;
    }

    public int getErrorCount() {
        return errorCount;
    }

    public String getSuccessMessage() {
        return successMessage;
    }


    public String getErrormessageMessage() {
        return errormessage;
    }

    public boolean isCardNumberValid() {
        return isCardNumberValid;
    }

    public boolean isCardHolderValid() {
        return isCardHolderValid;
    }

    public boolean isExpDateValid() {
        return isExpDateValid;
    }

    public boolean isCVCValid() {
        return isCVCValid;
    }

    public boolean isNavigatedToPreviousPage() {
        return navigatedToPreviousPage;
    }

    public boolean isNavigatedToHomeScreen() {
        return navigatedToHomeScreen;
    }

    @Override
    public void setCardNumberValidity(boolean correctNumber) {
        isCardNumberValid= correctNumber;
        if(!correctNumber){
            errorCount++;
        }

    }

    @Override
    public void setCardHolderValidity(boolean correctName) {
        isCardHolderValid = correctName;
        if(!correctName){
            errorCount++;
        }

    }

    @Override
    public void setExpDateValidity(boolean correctDate) {
        isExpDateValid = correctDate;
        if(!correctDate){
            errorCount++;
        }

    }

    @Override
    public void setCVCValidity(boolean correctCVC) {
        isCVCValid = correctCVC;
        if(!correctCVC){
            errorCount++;
        }

    }

    @Override
    public void showPaymentSuccess(String successmsg) {
        successMessage = successmsg;

    }

    @Override
    public void navigateToPreviousPage() {
        navigatedToPreviousPage = true;

    }

    @Override
    public void confirmPay() {
        PayConfirmed = true;

    }

    @Override
    public void returnToHomeScreen() {
        navigatedToHomeScreen = true;


    }
}

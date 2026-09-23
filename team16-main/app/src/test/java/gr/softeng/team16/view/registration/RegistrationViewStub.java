package gr.softeng.team16.view.registration;

public class RegistrationViewStub implements RegistrationView{
    private int errorCount = 0;
    private String errormessage = "";
    private boolean isSuccess = false;
    private boolean IsUsernameValid = true;

    private boolean navigatedTomain = false;

    public int getErrorCount() {
        return errorCount;
    }

    public String getErrormessage() {
        return errormessage;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public boolean isUsernameValid() {
        return IsUsernameValid;
    }

    public boolean isNavigatedTomain() {
        return navigatedTomain;
    }

    @Override
    public void showUsernameError(String message) {
        errorCount++;
        errormessage = message;

    }

    @Override
    public void showEmailError(String message) {
        errorCount++;
        errormessage = message;

    }

    @Override
    public void onRegistrationSuccess(String username) {
        isSuccess = true;

    }

    @Override
    public void showErrorMessage(String message) {
        errormessage = message;
        errorCount++;

    }

    @Override
    public void setUsernameValid(boolean isvalid) {
        IsUsernameValid = isvalid;
        if (!isvalid){
            errorCount++;
        }

    }

    @Override
    public void setEmailValid(boolean isvalid) {

    }

    @Override
    public void navigateToMain() {
        navigatedTomain = true;

    }
}


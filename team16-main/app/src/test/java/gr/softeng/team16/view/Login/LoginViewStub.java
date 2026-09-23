package gr.softeng.team16.view.Login;

public class LoginViewStub implements  LoginView{
    private int errorCount = 0;
    private String successMessage = "";
    private boolean isUsernameValid = true;

    private boolean isPasswordCorrect = true;

    private boolean navigatedTomain = false;

    private boolean loginConfirmed = false;

    public boolean adminLoginConfirmed = false;

    public int getErrorCount() {
        return errorCount;
    }

    public String getSuccessMessage() {
        return successMessage;
    }

    public boolean isUsernameValid() {
        return isUsernameValid;
    }

    public boolean isPasswordCorrect() {
        return isPasswordCorrect;
    }

    public boolean isNavigatedTomain() {
        return navigatedTomain;
    }

    public boolean isLoginConfirmed() {
        return loginConfirmed;
    }

    public boolean isAdminLoginConfirmed() {
        return adminLoginConfirmed;
    }

    @Override
    public void setUsernameValidity(boolean exist) {
        isUsernameValid = exist;
        if(!exist){
            errorCount++;
        }

    }

    @Override
    public void setPasswordValidity(boolean correct) {
        isPasswordCorrect = correct;
        if(!correct){
            errorCount++;
        }

    }

    @Override
    public void showLoginSuccess(String message) {
        successMessage = message;

    }

    @Override
    public void navigateToMain() {
        navigatedTomain = true;

    }

    @Override
    public void confirmLogin() {
        loginConfirmed = true;

    }

    @Override
    public void confirmAdminLogin() {
        adminLoginConfirmed = true;

    }


}

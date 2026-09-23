package gr.softeng.team16.view.registration;

/**
 * Mediates interaction between the registration logic and the user interface.
 */
public interface RegistrationView {

    /**
     * Reports validation issues specifically for the username.
     * @param message error description to be shown.
     */
    void showUsernameError(String message);

    /**
     * Reports validation issues specifically for the email.
     * @param message error description to be shown.
     */
    void showEmailError(String message);

    /**
     * Confirms successful account registration to the view.
     * @param username Username of the new registered account.
     */
    void onRegistrationSuccess(String username);

    /**
     * Displays a general error message to the user.
     * @param message content of the error message.
     */
    void showErrorMessage(String message);

    /**
     * Adjusts the username's display status to match its validation outcome.
     * Offers live visual cues for better interaction.
     * @param isvalid True if username available, else false
     */
    void setUsernameValid(boolean isvalid);

    /**
     * Adjusts the email field's appearance based on its validation outcome.
     * Offers live visual cues for better interaction.
     * @param isvalid True if email available, else false
     */
    void setEmailValid(boolean isvalid);

    /**
     * Navigates the user back to the main landing page.
     */
    void navigateToMain();


}

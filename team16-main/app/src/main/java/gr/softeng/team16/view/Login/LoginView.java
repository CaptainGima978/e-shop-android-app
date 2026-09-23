package gr.softeng.team16.view.Login;

import com.google.firebase.database.DatabaseError;

/**
 * Defines how the Presenter communicates with the login screen to show results like success or errors.
 */
public interface LoginView {

    /**
     * Shows the user if the username they typed is already taken or available.
     * @param exist exist True if the username is found, false otherwise.
     */
    void setUsernameValidity(boolean exist);

    /**
     *Shows the user if the password they entered is right or wrong.
     * @param correct True if the password is valid, false otherwise.
     */
    void setPasswordValidity(boolean correct);

    /**
     * Shows a success message once the user logs in.
     * @param message The success message to be shown
     */
    void showLoginSuccess(String message);

    /**
     * Navigates the user away from the login screen, usually returning to the main menu.
     */
    void navigateToMain();

    /**
     * Confirms the Login and navigates to Home Screen
     */
    void confirmLogin();

    /**
     * Confirms the Admin Login and navigates to Admin Home Screen
     */
    void confirmAdminLogin();


}

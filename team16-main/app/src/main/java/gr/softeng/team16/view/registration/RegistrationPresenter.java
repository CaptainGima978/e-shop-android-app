package gr.softeng.team16.view.registration;

import com.google.firebase.database.DatabaseError;

import java.util.HashMap;
import java.util.Map;

import gr.softeng.team16.data.AuthCallback;
import gr.softeng.team16.data.DataRepository;
import gr.softeng.team16.data.LoadCallback;

/**
 * Presenter for the registration function.
 * Logic for user registration.
 * Communication with Firebase and Validation.
 */
public class RegistrationPresenter {
    private RegistrationView view;

    private final DataRepository repo;

    /**
     * Default constructor.
     */
    public RegistrationPresenter(DataRepository repo) {

        this.repo = repo;
    }

    /**
     * Initialize Firebase dependecies.
     * @param view for user storage.
     */

    public void setView(RegistrationView view){
        this.view = view;
    }

    /**
     * Validation and registration of a new user in Firebase.
     * Check username uniqueness and creation of user.
     * @param fName first name of user.
     * @param lName last name of user.
     * @param email email adress of user.
     * @param username username of user.
     * @param pass password of user.
     */
    public void CheckRegister(String fName, String lName, String email, String username, String pass) {
        repo.checkUsernameAvailability(username, new LoadCallback<Boolean>() {
            @Override
            public void onLoaded(Boolean isAvailable) {
                if (isAvailable) {

                    AuthRegistration(fName, lName, email, username, pass);
                } else {
                    if (view != null) view.showUsernameError("Username already taken.");
                }
            }

            @Override
            public void onError(DatabaseError error) {
                if (view != null) {
                    String errorMessage = (error != null) ? error.getMessage() : "Database Error";
                    view.showErrorMessage("Database Error: " + errorMessage);
                }
            }
        });

    }
    /**
     * Checks if username is taken in real time in the Firebase database.
     * Reflects real-time UI updates to enhance user interactivity.
     * @param username username for database verification.
     */
    public void checkUsernameLive(String username) {
        repo.checkUsernameAvailability(username, new LoadCallback<Boolean>() {
            @Override
            public void onLoaded(Boolean isAvailable) {
                if (view != null){
                    view.setUsernameValid(isAvailable);

                }

            }

            @Override
            public void onError(DatabaseError error) {
                if (view != null) {
                    view.setUsernameValid(false);

                }

            }
        });
    }

    /**
     * Checks if email is already registered in real-time in the Firebase database.
     * Τhis prevents account duplication by verifying email uniqueness.
     * @param email
     */
    public void checkEmailLive(String email) {
        repo.checkEmailAvailability(email, new LoadCallback<Boolean>() {
            @Override
            public void onLoaded(Boolean isAvailable) {
                 if (view != null) {

                    view.setEmailValid(isAvailable);
                }

            }

            @Override
            public void onError(DatabaseError error) {
                if (view != null) view.setEmailValid(false);
            }
        });
    }
    /**
     * Authenticates and creates a new user account using Firebase Authentication.
     * Once the account is created, further user details are committed to the database.
     * @param firstName First name field for remote storage.
     * @param lastName Last name field for remote storage.
     * @param email Email address used as login identifiers.
     * @param username Unique username for the user's profile.
     * @param password Secure passowrd for account authentication.
     */
    public void AuthRegistration(String firstName, String lastName, String email, String username, String password){

        Map<String, Object> userMap = new HashMap<>();
        userMap.put("firstName", firstName);
        userMap.put("lastName", lastName);
        userMap.put("email", email);
        userMap.put("username", username);

        repo.registerUser(email, password, userMap, new AuthCallback() {
            @Override
            public void onSuccess(String resultEmail) {
                if (view != null) view.onRegistrationSuccess(username);
            }

            @Override
            public void onFailure(String message) {
                if (view != null) {
                    view.showEmailError("Registration failed: " + message);
                }
            }
        });
    }

    /**
     * Cancellation of registration process.
     */
    public void onCancelClick(){
        view.navigateToMain();
    }


}

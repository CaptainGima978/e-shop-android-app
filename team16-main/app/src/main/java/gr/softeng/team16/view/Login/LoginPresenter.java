package gr.softeng.team16.view.Login;

import com.google.firebase.database.DatabaseError;

import gr.softeng.team16.data.AuthCallback;
import gr.softeng.team16.data.DataRepository;
import gr.softeng.team16.data.LoadCallback;

/**
 * Presenter class for the Login screen.
 * Handles user signup by checking username availability and authenticating via Firebase.
 */
public class LoginPresenter {
    private LoginView view;

    private final DataRepository repo;
    private final String[] admins = {"admin@build.com"};

    /**
     * Default Constructor.
     */
    public LoginPresenter(DataRepository repo){

        this.repo = repo;
    }



    public void setView(LoginView view){
        this.view = view;
    }



    /**
     * Initiates the login process.
     * Looks up the email for the given username and then signs the user in.
     * @param username Provided username.
     * @param password Provided password.
     */
    public void onLoginClick(String username, String password) {

        if (username == null || username.trim().isEmpty()) {
            view.setUsernameValidity(false);
            return;
        }
        if (password == null || password.trim().isEmpty()) {
            view.setPasswordValidity(false);
            return;
        }

        repo.getEmailByUsername(username, new LoadCallback<String>() {
            @Override
            public void onLoaded(String emailFound) {

                authenticateUser(emailFound, password);
            }

            @Override
            public void onError(DatabaseError error) {

                view.setUsernameValidity(false);
            }
        });
    }

    /**
     * The last step: logging in with Firebase using email and password.
     * @param email user's retrieved email.
     * @param password user's input password.
     */
    private void authenticateUser(String email, String password) {
        repo.authenticateUser(email, password, new AuthCallback() {
            @Override
            public void onSuccess(String authenticatedEmail) {
                if (view == null) return;

                view.setPasswordValidity(true);

                if (isAdmin(authenticatedEmail)) {
                    view.showLoginSuccess("Admin Login Success");
                    view.confirmAdminLogin();
                } else {
                    view.showLoginSuccess("Login Success");
                    view.confirmLogin();
                }
            }

            @Override
            public void onFailure(String message) {
                if (view != null) {

                    view.setPasswordValidity(false);
                }
            }
        });
    }

    public void onUsernameChanged(String username) {


        repo.getEmailByUsername(username, new LoadCallback<String>() {
            @Override
            public void onLoaded(String email) {
                if (view != null) {
                    view.setUsernameValidity(true);
                }
            }

            @Override
            public void onError(DatabaseError error) {
                if (view != null) {

                    view.setUsernameValidity(false);
                }
            }
        });
    }

    /**
     * Handles the cancellation of the login process.
     */
    public void onCancelClick(){
        view.navigateToMain();
    }



    private boolean isAdmin(String email) {
        for (String adminEmail : admins) {
            if (adminEmail.equals(email)) {
                return true;
            }
        }
        return false;
    }


}



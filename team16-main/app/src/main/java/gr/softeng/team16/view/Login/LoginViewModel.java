package gr.softeng.team16.view.Login;

import androidx.lifecycle.ViewModel;

import gr.softeng.team16.data.FirebaseRepository;

/**
 * ViewModel class for the Login screen.
 * Keeps the LoginPresenter alive even when the screen rotates.
 */
public class LoginViewModel extends ViewModel {

    private LoginPresenter presenter;

    /**
     * Provides the current instance of the LoginPresenter.
     *  If the presenter does not exist, it initializes a new one.
     * @return The active LoginPresenter instance.
     */
    public LoginPresenter getPresenter(){
        if(presenter == null){
            presenter = new LoginPresenter(FirebaseRepository.getInstance()); //Initialize a new presenter if it doesn't exist.
        }
        return presenter; // Return the existing presenter to maintain the state of the view.
    }
}

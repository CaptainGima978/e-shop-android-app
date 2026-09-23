package gr.softeng.team16.view.registration;

import androidx.lifecycle.ViewModel;

import gr.softeng.team16.data.FirebaseRepository;

/**
 * Viewmodel class for the Registration screen.
 */
public class RegistrationViewModel extends ViewModel {

    private RegistrationPresenter presenter;

    /**
     * Retrieves the initialized RegistrationPresenter.
     * If the presenter does not exist, it initializes a new one.
     * @return active RegistrationPresenter.
     */
    public RegistrationPresenter getPresenter() {
        if (presenter == null) {
            //Create a new instance if it's the first time the activity starts.
            presenter = new RegistrationPresenter(FirebaseRepository.getInstance());
        }
        return  presenter;//Retains the same instance through lifecycle events.
    }
}

package gr.softeng.team16.view.cart.personalDetails;

import androidx.lifecycle.ViewModel;

import gr.softeng.team16.data.FirebaseRepository;

/**
 * ViewModel class for the fillPersonalDetails screen.
 * Keeps the fillPersonalDetailsPresenter alive even when the screen rotates.
 */

public class fillPersonalDetailsViewModel extends ViewModel {
    private fillPersonalDetailsPresenter presenter;

    /**
     * Provides the current instance of the fillPersonalDetailsPresenter.
     *  If the presenter does not exist, it initializes a new one.
     * @return The active fillPersonalDetailsPresenter instance.
     */

    public fillPersonalDetailsPresenter getPresenter() {
        if (presenter == null) {
            presenter = new fillPersonalDetailsPresenter(FirebaseRepository.getInstance()); //Initialize a new presenter if it doesn't exist.
        }
        return presenter; // Return the existing presenter to maintain the state of the view.
    }
}

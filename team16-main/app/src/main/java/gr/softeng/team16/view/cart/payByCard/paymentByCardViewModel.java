package gr.softeng.team16.view.cart.payByCard;

import androidx.lifecycle.ViewModel;

import gr.softeng.team16.data.FirebaseRepository;

/**
 * ViewModel class for the paymentByCard screen.
 * Keeps the paymentByCardPresenter alive even when the screen rotates.
 */
public class paymentByCardViewModel extends ViewModel {
    private paymentByCardPresenter presenter;

    /**
     * Provides the current instance of the paymentByCardPresenter.
     *  If the presenter does not exist, it initializes a new one.
     * @return The active paymentByCardPresenter instance.
     */
    public paymentByCardPresenter getPresenter(){
        if(presenter == null){
            presenter = new paymentByCardPresenter(FirebaseRepository.getInstance()); //Initialize a new presenter if it doesn't exist.
        }
        return presenter; // Return the existing presenter to maintain the state of the view.
    }
}

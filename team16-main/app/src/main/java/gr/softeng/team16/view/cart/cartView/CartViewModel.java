package gr.softeng.team16.view.cart.cartView;

import androidx.lifecycle.ViewModel;

import gr.softeng.team16.data.FirebaseRepository;
import gr.softeng.team16.view.cart.payByCard.paymentByCardPresenter;

public class CartViewModel extends ViewModel {
    private CartPresenter presenter;

    public CartViewModel() {

        this.presenter = CartPresenter.getInstance();
    }

    /**
     * Provides the current instance of the CartPresenter.
     *  If the presenter does not exist, it initializes a new one.
     * @return The active CartPresenter instance.
     */
    public CartPresenter getPresenter(){
        if(presenter == null){
            presenter = new CartPresenter(FirebaseRepository.getInstance()); //Initialize a new presenter if it doesn't exist.
        }
        return presenter; // Return the existing presenter to maintain the state of the view.
    }
}

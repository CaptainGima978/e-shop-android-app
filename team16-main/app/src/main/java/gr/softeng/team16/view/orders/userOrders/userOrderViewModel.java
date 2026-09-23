package gr.softeng.team16.view.orders.userOrders;


import androidx.lifecycle.ViewModel;

import gr.softeng.team16.view.cart.cartView.CartPresenter;
import gr.softeng.team16.view.cart.payByCard.paymentByCardPresenter;
public class userOrderViewModel extends ViewModel {

    private userOrderPresenter presenter;

    public userOrderViewModel() {
        this.presenter = userOrderPresenter.getInstance();
    }

    /**
     * Provides the current instance of the userOrderPresenter.
     *  If the presenter does not exist, it initializes a new one.
     * @return The active userOrderPresenter instance.
     */
    public userOrderPresenter getPresenter(){
        if(presenter == null){
            presenter = new userOrderPresenter(); //Initialize a new presenter if it doesn't exist.
        }
        return presenter; // Return the existing presenter to maintain the state of the view.
    }
}

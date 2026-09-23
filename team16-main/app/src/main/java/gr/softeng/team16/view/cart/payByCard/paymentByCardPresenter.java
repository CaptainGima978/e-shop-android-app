package gr.softeng.team16.view.cart.payByCard;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import gr.softeng.team16.data.DataRepository;
import gr.softeng.team16.domain.Address;
import gr.softeng.team16.domain.Cart;
import gr.softeng.team16.domain.Order;
import gr.softeng.team16.domain.PaymentService;
import gr.softeng.team16.view.build.BuildPresenter;
import gr.softeng.team16.view.cart.cartView.CartPresenter;

public class paymentByCardPresenter {

    private paymentByCardView view;
    private DataRepository repo;

    public paymentByCardPresenter(DataRepository repo) {
        this.repo = repo;
    }

    public void setView(paymentByCardView view) {
        this.view = view;
    }

    public void checkCardNumber(String cardNum) {
        if (cardNum == null) {
            view.setCardNumberValidity(false);
            return;
        }
        boolean isValid = cardNum.matches("^\\d{16}$");
        view.setCardNumberValidity(isValid);
    }

    public void checkCardHolder(String name) {
        if (name == null) {
            view.setCardHolderValidity(false);
            return;
        }
        boolean isValid = name.matches("^[a-zA-Z\\s]+$");
        view.setCardHolderValidity(isValid);
    }

    public void checkExpDate(String date) {
        if (date == null) {
            view.setExpDateValidity(false);
            return;
        }
        boolean isExpired = PaymentService.isCardExpired(date);
        view.setExpDateValidity(!isExpired);
    }

    public void checkCVC(String cvc) {
        if (cvc == null) {
            view.setCVCValidity(false);
            return;
        }
        boolean isValid = cvc.matches("^\\d{3}$");
        view.setCVCValidity(isValid);
    }

    public void onCancelClick() {
        view.navigateToPreviousPage();
    }

    public void onPayClick(String cardNumber, String cardHolder, String expDate, String cvc, Address address) {
        CartPresenter.getInstance().placeOrder(address);
    }
}

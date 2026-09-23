package gr.softeng.team16.view.cart.personalDetails;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import gr.softeng.team16.data.DataRepository;
import gr.softeng.team16.domain.Address;

public class fillPersonalDetailsPresenter {
    private fillPersonalDetailsView view;
    private DatabaseReference database;
    private FirebaseAuth auth;

    private final DataRepository repo;

    public fillPersonalDetailsPresenter(DataRepository repo) {
        this.repo = repo;
    }

    public void initFirebase(DatabaseReference db, FirebaseAuth auth) {
        this.database = db;
        this.auth = auth;
    }

    public void setView(fillPersonalDetailsView view) {
        this.view = view;
    }

    public void checkName(String name) {
        boolean isValid = name != null && name.matches("^[a-zA-Z\\s]+$");
        view.setNameValidity(isValid);
    }

    public void checkSurname(String surname) {
        boolean isValid = surname != null && surname.matches("^[a-zA-Z\\s]+$");
        view.setSurnameValidity(isValid);
    }

    public void checkAddress(String address) {
        // Using domain logic
        view.setAddressValidity(Address.checkAddress_validity(address));
    }

    public void checkCity(String city) {
        // Using domain logic
        view.setCityValidity(Address.checkCity_validity(city));
    }

    public void checkZipCode(String zipCode) {
        // Using domain logic
        view.setZipCodeValidity(Address.checkZipcode_validity(zipCode));
    }

    public void checkEmail(String email) {
        if (auth != null && auth.getCurrentUser() != null) {
            String userEmail = auth.getCurrentUser().getEmail();
            view.setEmailValidity(email != null && email.equalsIgnoreCase(userEmail));
        } else {
            view.setEmailValidity(false);
        }
    }

    public void onContinueClick(String name, String surname, String fullAddress, String city, String zipCode, String email) {
        view.navigateToPayment();
    }

    public void onCancelClick() {
        view.navigateToHomePage();
    }
}

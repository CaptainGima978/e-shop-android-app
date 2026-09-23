package gr.softeng.team16.view.cart.personalDetails;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import gr.softeng.team16.R;
import gr.softeng.team16.domain.Address;
import gr.softeng.team16.view.cart.payByCard.paymentByCardActivity;
import gr.softeng.team16.view.home.HomeActivity;

public class fillPersonalDetailsActivity extends AppCompatActivity implements fillPersonalDetailsView {

    private fillPersonalDetailsViewModel viewModel;
    private fillPersonalDetailsPresenter presenter;

    private EditText nameInput, surnameInput, addressInput, cityInput, zipCodeInput, emailInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_fill_personal_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize views
        nameInput = findViewById(R.id.nameFill);
        surnameInput = findViewById(R.id.surnameFill);
        addressInput = findViewById(R.id.addressFill);
        cityInput = findViewById(R.id.cityFill);
        zipCodeInput = findViewById(R.id.zipCodeFill);
        emailInput = findViewById(R.id.emailFill);

        Button btnCancel = findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(v -> presenter.onCancelClick());

        Button btnContinue = findViewById(R.id.btnContinue);
        btnContinue.setOnClickListener(v -> confirmContinue());

        viewModel = new ViewModelProvider(this).get(fillPersonalDetailsViewModel.class);
        presenter = viewModel.getPresenter();
        presenter.setView(this);

        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        presenter.initFirebase(db, auth);

        setupListeners();
    }

    private void setupListeners() {
        nameInput.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                presenter.checkName(s.toString().trim());
            }
        });

        surnameInput.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                presenter.checkSurname(s.toString().trim());
            }
        });

        addressInput.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                presenter.checkAddress(s.toString().trim());
            }
        });

        cityInput.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                presenter.checkCity(s.toString().trim());
            }
        });

        zipCodeInput.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                presenter.checkZipCode(s.toString().trim());
            }
        });

        emailInput.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                presenter.checkEmail(s.toString().trim());
            }
        });
    }

    @Override
    public void setNameValidity(boolean correctName) {
        updateFieldUI(nameInput, correctName, "Invalid name");
    }

    @Override
    public void setSurnameValidity(boolean correctSurname) {
        updateFieldUI(surnameInput, correctSurname, "Invalid surname");
    }

    @Override
    public void setAddressValidity(boolean correctAddress) {
        updateFieldUI(addressInput, correctAddress, "Address must be: Name [Space] 1-3 Digits");
    }

    @Override
    public void setCityValidity(boolean correctCity) {
        updateFieldUI(cityInput, correctCity, "Invalid city");
    }

    @Override
    public void setZipCodeValidity(boolean correctZipCode) {
        updateFieldUI(zipCodeInput, correctZipCode, "Zip code must be 5 digits");
    }

    @Override
    public void setEmailValidity(boolean correctEmail) {
        updateFieldUI(emailInput, correctEmail, "Email does not match account");
    }

    private void updateFieldUI(EditText input, boolean isValid, String errorMsg) {
        if (input.getText().toString().isEmpty()) {
            input.getBackground().clearColorFilter();
            input.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0);
            input.setError(null);
            return;
        }
        if (isValid) {
            input.getBackground().setColorFilter(getResources().getColor(android.R.color.holo_green_light), PorterDuff.Mode.SRC_IN);
            input.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.baseline_check_circle_24, 0);
            input.setError(null);
        } else {
            input.getBackground().setColorFilter(getResources().getColor(android.R.color.holo_red_light), PorterDuff.Mode.SRC_IN);
            input.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0);
            input.setError(errorMsg);
        }
    }

    @Override
    public void confirmContinue() {
        String name = nameInput.getText().toString().trim();
        String surname = surnameInput.getText().toString().trim();
        String fullAddress = addressInput.getText().toString().trim();
        String city = cityInput.getText().toString().trim();
        String zipCode = zipCodeInput.getText().toString().trim();
        String email = emailInput.getText().toString().trim();

        boolean hasError = false;

        if (name.isEmpty()) {
            nameInput.setError("Name required");
            hasError = true;
        }
        if (surname.isEmpty()) {
            surnameInput.setError("Surname required");
            hasError = true;
        }
        if (fullAddress.isEmpty()) {
            addressInput.setError("Address required");
            hasError = true;
        }
        if (city.isEmpty()) {
            cityInput.setError("City name required");
            hasError = true;
        }

        if (zipCode.isEmpty()) {
            zipCodeInput.setError("Zipcode required");
            hasError = true;
        }

        if (email.isEmpty()) {
            emailInput.setError("Email required");
            hasError = true;
        }

        if (!hasError) {
            presenter.onContinueClick(name, surname, fullAddress, city, zipCode, email);
        }
    }

    @Override
    public void navigateToPayment() {
        String name = nameInput.getText().toString().trim();
        String surname = surnameInput.getText().toString().trim();
        String fullAddress = addressInput.getText().toString().trim();
        String city = cityInput.getText().toString().trim();
        String zipCode = zipCodeInput.getText().toString().trim();
        String email = emailInput.getText().toString().trim();

        Intent intent = new Intent(this, paymentByCardActivity.class);
        intent.putExtra("address", fullAddress);
        intent.putExtra("city", city);
        intent.putExtra("zipCode", zipCode);
        startActivity(intent);
        finish();
    }

    @Override
    public void navigateToHomePage() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void showErrorMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private abstract static class SimpleTextWatcher implements TextWatcher {
        @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        @Override public void afterTextChanged(Editable s) {}
    }
}

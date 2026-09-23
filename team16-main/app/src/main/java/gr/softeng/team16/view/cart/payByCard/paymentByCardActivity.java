package gr.softeng.team16.view.cart.payByCard;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
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
import gr.softeng.team16.view.cart.cartView.CartPresenter;
import gr.softeng.team16.view.cart.personalDetails.fillPersonalDetailsActivity;
import gr.softeng.team16.view.home.HomeActivity;

public class paymentByCardActivity extends AppCompatActivity implements paymentByCardView {

    private paymentByCardViewModel viewModel;
    private paymentByCardPresenter presenter;
    private EditText cardNumberInput;
    private EditText cardHolderInput;
    private EditText expDateInput;
    private EditText CVCInput;
    private Address address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_payment_by_card);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        String zipcode = getIntent().getStringExtra("zipCode");
        String city = getIntent().getStringExtra("city");
        String fullAddress = getIntent().getStringExtra("address");

        address = new Address(city, fullAddress.split(" ")[0], Integer.parseInt(fullAddress.split(" ")[1]), zipcode);

        // Initialize buttons

        Button btnPayConfirm = findViewById(R.id.btnPayConfirm);
        btnPayConfirm.setOnClickListener(v -> confirmPay());

        Button btnCancel2 = findViewById(R.id.btnCancel2);
        btnCancel2.setOnClickListener(v -> {
            presenter.onCancelClick();
        });

        // Initialize the fields
        cardNumberInput = findViewById(R.id.cardNumberFill);
        cardHolderInput = findViewById(R.id.cardHolderFill);
        expDateInput = findViewById(R.id.expDateFill);
        CVCInput = findViewById(R.id.CVCFill);

        //Preserve presenter using ViewModel
        viewModel = new ViewModelProvider(this).get(paymentByCardViewModel.class);
        presenter = viewModel.getPresenter();
        presenter.setView(this);


        setupListeners();
    } //end of onCreate

    @Override
    public void setCardNumberValidity(boolean correctNumber) {
        updateFieldUI(cardNumberInput, correctNumber, "Incorrect number");
    }

    @Override
    public void setCardHolderValidity(boolean correctName) {
        updateFieldUI(cardHolderInput, correctName, "Invalid name");
    }

    @Override
    public void setExpDateValidity(boolean correctDate) {
        updateFieldUI(expDateInput, correctDate, "Invalid date");
    }

    @Override
    public void setCVCValidity(boolean correctCVC) {
        updateFieldUI(CVCInput, correctCVC, "Incorrect number");
    }

    /**
     * Helper method to update the UI of an EditText based on validity.
     */
    private void updateFieldUI(EditText input, boolean isValid, String errorMessage) {
        if (isValid) {
            input.getBackground().setColorFilter(getResources().getColor(android.R.color.holo_green_light), PorterDuff.Mode.SRC_IN);
            input.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.baseline_check_circle_24, 0);
            input.setError(null);
        } else {
            input.getBackground().setColorFilter(getResources().getColor(android.R.color.holo_red_light), PorterDuff.Mode.SRC_IN);
            input.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0);
            input.setError(errorMessage);
        }
    }

    @Override
    public void confirmPay() {
        String cardNumber = cardNumberInput.getText().toString().trim();
        String cardHolder = cardHolderInput.getText().toString().trim();
        String expDate = expDateInput.getText().toString().trim();
        String CVC = CVCInput.getText().toString().trim();

        boolean hasError = false;

        if (cardNumber.isEmpty()) {
            cardNumberInput.setError("Card number required");
            hasError = true;
        }
        if (cardHolder.isEmpty()) {
            cardHolderInput.setError("Card holder required");
            hasError = true;
        }
        if (expDate.isEmpty()) {
            expDateInput.setError("exp. Date required");
            hasError = true;
        }
        if (CVC.isEmpty()) {
            CVCInput.setError("CVC required");
            hasError = true;
        }

        if (!hasError) {
            presenter.onPayClick(cardNumber, cardHolder, expDate, CVC, address);
            setResult(RESULT_OK);
            finish();
        }
    }

    @Override
    public void navigateToPreviousPage() {
        Intent intent = new Intent(this, fillPersonalDetailsActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void showPaymentSuccess(String successmsg) {
        Toast.makeText(paymentByCardActivity.this, "Order placed!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void returnToHomeScreen() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    private void setupListeners() {
        cardNumberInput.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text = s.toString().trim();
                if (!text.isEmpty()) {
                    presenter.checkCardNumber(text);
                } else {
                    clearFieldStatus(cardNumberInput);
                }
            }
        });

        cardHolderInput.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text = s.toString().trim();
                if (!text.isEmpty()) {
                    presenter.checkCardHolder(text);
                } else {
                    clearFieldStatus(cardHolderInput);
                }
            }
        });

        expDateInput.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text = s.toString().trim();
                if (!text.isEmpty()) {
                    presenter.checkExpDate(text);
                } else {
                    clearFieldStatus(expDateInput);
                }
            }
        });

        CVCInput.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text = s.toString().trim();
                if (!text.isEmpty()) {
                    presenter.checkCVC(text);
                } else {
                    clearFieldStatus(CVCInput);
                }
            }
        });
    }

    private void clearFieldStatus(EditText input) {
        input.getBackground().clearColorFilter();
        input.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0);
        input.setError(null);
    }

    private abstract static class SimpleTextWatcher implements TextWatcher {
        @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        @Override public void afterTextChanged(Editable s) {}
    }
}

package gr.softeng.team16.view.registration;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import gr.softeng.team16.R;
import gr.softeng.team16.view.Login.LoginActivity;

/**
 * Manages the account registration interface and UI logic.
 */

public class RegistrationActivity extends AppCompatActivity implements RegistrationView {

    private EditText edtFirstName, edtLastName, edtEmail, edtUsername, edtPassword, edtConfirmPassword;

    private TextView tvterms;

    private RegistrationViewModel viewModel;

    private RegistrationPresenter presenter;
    private CheckBox cbterms;
    private Button btnregister, btncancel;
    private TextView tvLoginLink;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registration);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //Initialize UI components and listeners.
        initializeViews();

        //Retrieves a lifecycle-persistent Presenter via the ViewModel provider.
        RegistrationViewModel viewModel = new ViewModelProvider(this).get(RegistrationViewModel.class);

        presenter = viewModel.getPresenter();

        presenter.setView(this);

        //Sets up Firebase instances to be used by the presenter.
        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        FirebaseAuth auth = FirebaseAuth.getInstance();



        setupListeners();
    }

    /**
     * Links UI components from XML layout to Java objects.
     */
    private void initializeViews(){
        edtFirstName = findViewById(R.id.edt_first_name);
        edtLastName = findViewById(R.id.edt_last_name);
        edtUsername = findViewById(R.id.edt_username);
        edtEmail = findViewById(R.id.edt_email);
        edtPassword = findViewById(R.id.edt_password);
        edtConfirmPassword = findViewById(R.id.edt_confirm_password);

        tvLoginLink = findViewById(R.id.tv_login_link);

        cbterms = findViewById(R.id.cb_terms);
        btnregister = findViewById(R.id.btn_register);
        btncancel = findViewById(R.id.btn_cancel);

        tvterms = findViewById(R.id.tv_terms);

    }


    /**
     * Shows a dedicated error for the username field.
     * @param message error description.
     */
    @Override
    public void showUsernameError(String message) {
        runOnUiThread(() -> {
            edtUsername.setError(message);
            edtUsername.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.holo_red_light), PorterDuff.Mode.SRC_IN);
            edtUsername.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        });

    }

    /**
     * Shows a dedicated error for the email field.
     * @param message error description.
     */
    @Override
    public void showEmailError(String message) {
        edtEmail.setError(message);
        edtEmail.getBackground().clearColorFilter(); //Remove any color filters from the background
        edtEmail.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0); // Clear any icons
        edtEmail.requestFocus(); //Auto-focuses the field for quick correction.

    }

    /**
     * Registration success handler.
     * Navigates the user to the Login screen.
     * @param username name of registered user.
     */
    @Override
    public void onRegistrationSuccess(String username) {
        Toast.makeText(RegistrationActivity.this, "Registration successful! Welcome, " + username + "!", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();

    }

    /**
     * eroor message using Toast.
     * @param message error contert.
     */
    @Override
    public void showErrorMessage(String message) {
        Toast.makeText(RegistrationActivity.this, message, Toast.LENGTH_LONG).show();

    }

    /**
     * Indicates whether the chosen username is valid.
     * @param isvalid True is userame available, else false.
     */
    @Override
    public void setUsernameValid(boolean isvalid) {
        runOnUiThread(() -> {
            if (isvalid) {

                edtUsername.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.holo_green_light), PorterDuff.Mode.SRC_IN);
                edtUsername.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.baseline_check_circle_24, 0);
                edtUsername.setError(null);
            } else {
                showUsernameError("Username already taken");
            }
        });
    }

    /**
     * Toggles email validity indicators after a database check.
     * Uses icons and color tints for immediate UI response.
     * True if the email is unique and available for registration, false otherwise.
     * @param isValid
     */
    public void setEmailValid(boolean isValid) {
        runOnUiThread(() -> {
            if (isValid) {

                edtEmail.getBackground().setColorFilter(ContextCompat.getColor(this, android.R.color.holo_green_light), PorterDuff.Mode.SRC_IN);
                edtEmail.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.baseline_check_circle_24, 0);
                edtEmail.setError(null);
            } else {

                showEmailError("The email address is already in use.");

                edtEmail.getBackground().clearColorFilter();
                edtEmail.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            }
        });
    }

    /**
     * Navigates back to the main/login screen and closes the current activity.
     */
    @Override
    public void navigateToMain() {
        finish();
    }

    /**
     * Binds listeners and watchers to all interactive UI elements.
     */
    public void setupListeners(){

        setupValidation(edtFirstName);
        setupValidation(edtLastName);
        setupValidation(edtUsername);
        setupValidation(edtEmail);
        setupValidation(edtPassword);

        cbterms.setOnCheckedChangeListener((buttonView, isChecked) -> {;
            if (isChecked) {
                cbterms.setError(null);
            }
        });


        tvterms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(RegistrationActivity.this)
                        .setTitle("Terms and Conditions")
                        .setMessage("Welcome to the PC Builder community! By creating an account, you agree to follow the rules of our PC Building app and respect our community guidelines.")
                        .setPositiveButton("I UNDERSTAND", null)
                        .show();
            }
        });

        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onCancelClick();
            }
        });


        edtConfirmPassword.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String edt1 = edtPassword.getText().toString();
                String edt2 = s.toString();

                if (edt2.isEmpty()) {
                    edtConfirmPassword.getBackground().clearColorFilter();
                    edtConfirmPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                } else if (edt1.equals(edt2)) {
                    edtConfirmPassword.setError(null);
                    edtConfirmPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                    edtConfirmPassword.getBackground().setColorFilter(getResources().getColor(android.R.color.holo_green_light), PorterDuff.Mode.SRC_IN);
                    edtConfirmPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.baseline_check_circle_24, 0);

                } else {

                    edtConfirmPassword.getBackground().setColorFilter(getResources().getColor(android.R.color.holo_red_light), PorterDuff.Mode.SRC_IN);
                    edtConfirmPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                    edtConfirmPassword.setError("Passwords do not match");
                }
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void afterTextChanged(Editable s) {}

        });

        edtUsername.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String username = s.toString().trim();
                if (username.isEmpty()) {
                    edtUsername.getBackground().clearColorFilter();
                    edtUsername.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                } else {
                    presenter.checkUsernameLive(username);
                }

            }
            @Override public void afterTextChanged(Editable s) {}
        });

        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = edtFirstName.getText().toString().trim();
                String lastName = edtLastName.getText().toString().trim();
                String email = edtEmail.getText().toString().trim();
                String username = edtUsername.getText().toString().trim();
                String password = edtPassword.getText().toString();
                String confirmPassword = edtConfirmPassword.getText().toString();

                boolean hasError = false;

                if(firstName.isEmpty()){
                    edtFirstName.setError("First Name is required");
                    hasError = true;
                }

                if(lastName.isEmpty()){
                    edtLastName.setError("Last Name is required");
                    hasError = true;
                }

                if(email.isEmpty()){
                    edtEmail.setError("Email is required");
                    hasError = true;
                }

                if(username.isEmpty()){
                    edtUsername.setError("Username is required");
                    hasError = true;
                }

                if(password.isEmpty()){
                    edtPassword.setError("Password is required");
                    hasError = true;
                }

                if(confirmPassword.isEmpty()){
                    edtConfirmPassword.setError("Please confirm your password");
                    hasError = true;
                }

                if(hasError) {
                    return;
                }

                if(!password.equals(confirmPassword)){
                    edtConfirmPassword.setError("Passwords do not match");
                    return;

                }

                if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    edtEmail.setError("Please enter a valid email address");
                    edtEmail.getBackground().clearColorFilter();
                    edtEmail.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                    edtEmail.requestFocus();
                    return;
                }

                if(password.length() < 8){
                    edtPassword.setError("Password must be at least 8 characters long");
                    edtPassword.getBackground().clearColorFilter();
                    edtPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                    edtPassword.requestFocus();
                    return;
                }



                if(!cbterms.isChecked()){
                    cbterms.setError("You must agree to the terms and conditions");
                    cbterms.requestFocus();
                    cbterms.requestFocusFromTouch();
                    return;
                }

                presenter.CheckRegister(firstName, lastName, email, username, password);
            }

        });

        tvLoginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();

            }
        });


    }

    /**
     * Hooks a watcher to the input field for real-time validation.
     * @param editText view to monitor.
     */
    private void setupValidation(EditText editText){
        editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                String text = s.toString().trim();
                boolean isValid = true;
                String errorMessage = "";

                //Clears validation indicators for empty inputs.
                if (text.isEmpty()) {
                    editText.setError(null);
                    editText.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                    editText.getBackground().clearColorFilter();
                    return;
                }

                //Performs a live database lookup specifically for username availability.
                if(editText.getId()== R.id.edt_username){
                    presenter.checkUsernameLive(text);
                    return;
                }


                //Password validation logic for both password and confirmation fields.
                if(editText.getId() == R.id.edt_password || editText.getId() == R.id.edt_confirm_password){
                    if(text.length() < 8){
                        isValid = false;
                        errorMessage = "Password must be at least 8 characters long";
                    }

                }
                //Email format validation.
                else if(editText.getId() == R.id.edt_email){
                    if(!android.util.Patterns.EMAIL_ADDRESS.matcher(text).matches()){
                        isValid = false;
                        errorMessage = "Please enter a valid email address";
                    }else{
                        presenter.checkEmailLive(text);
                        return;
                    }
                }
                // Update the UI based on the validation result.
                if(isValid){
                    if (editText.getId() != R.id.edt_username && editText.getId() != R.id.edt_email) {
                        editText.getBackground().mutate().setColorFilter(ContextCompat.getColor(RegistrationActivity.this, android.R.color.holo_green_light),
                                PorterDuff.Mode.SRC_IN);
                        editText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.baseline_check_circle_24, 0);
                        editText.setError(null);
                    }
                }else{
                    editText.setError(errorMessage);
                    editText.getBackground().clearColorFilter();
                    editText.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                }

            }
            @Override
            public void afterTextChanged(Editable s) {
                editText.setOnFocusChangeListener(null);
            }


        });

    }

}
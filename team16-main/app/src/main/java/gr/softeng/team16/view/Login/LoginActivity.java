package gr.softeng.team16.view.Login;

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
import gr.softeng.team16.view.adminHome.AdminHomeActivity;
import gr.softeng.team16.view.home.HomeActivity;

/**
 * Handles all user interactions on the login screen.
 * Implements the LoginView interface to update the screen as directed by the presenter.
 */

public class LoginActivity extends AppCompatActivity implements LoginView {

    private EditText usernameInput, passwordInput;
    private Button btnlogin, btncancel;

    private LoginViewModel viewModel;
    private LoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initViews();

        //Preserve presenter using ViewModel
        LoginViewModel viewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        presenter = viewModel.getPresenter();

        presenter.setView(this);

        //Set up Firebase instances to be used by the presenter.
        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        FirebaseAuth auth = FirebaseAuth.getInstance();


        setupListeners();
    }

    /**
     * Links XML components to Java objects.
     */
    private void initViews() {
        usernameInput = findViewById(R.id.username_input);
        passwordInput = findViewById(R.id.password_input);
        btnlogin = findViewById(R.id.btn_login);
        btncancel = findViewById(R.id.btn_cancel);

    }

    /**
     * Changes how the username field looks depending on if the user is found in the system.
     * @param exist True if the username exists in the system, false otherwise.
     */
    @Override
    public void setUsernameValidity(boolean exist) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (exist) {
                    usernameInput.getBackground().mutate().setColorFilter(
                            getResources().getColor(android.R.color.holo_green_light),
                            PorterDuff.Mode.SRC_IN
                    );
                    usernameInput.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.baseline_check_circle_24, 0);
                    usernameInput.setError(null);
                } else {
                    usernameInput.getBackground().mutate().setColorFilter(
                            getResources().getColor(android.R.color.holo_red_light),
                            PorterDuff.Mode.SRC_IN
                    );
                    usernameInput.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                    //Apply red color filter and show "User not found" error

                    usernameInput.setError("User not found");

                }
            }
        });

    }

    /**
     * Shows the user if the password they entered is correct or incorrect.
     * @param correct True if the password matches the account, false otherwise.
     */
    @Override
    public void setPasswordValidity(boolean correct) {
        if (correct) {
            passwordInput.getBackground().setColorFilter(getResources().getColor(android.R.color.holo_green_light), PorterDuff.Mode.SRC_IN);
            passwordInput.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.baseline_check_circle_24, 0);
            passwordInput.setError(null);
        } else {
            passwordInput.getBackground().setColorFilter(getResources().getColor(android.R.color.holo_red_light), PorterDuff.Mode.SRC_IN);
            passwordInput.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            passwordInput.setError("Incorrect password");
        }


    }

    /**
     * Shows a quick success message once the user is logged in.
     * @param message The success message content.
     */
    @Override
    public void showLoginSuccess(String message) {
        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();

    }

    /**
     * Closes the login screen and navigates the user back to the previous activity.
     */
    @Override
    public void navigateToMain() {
        finish();

    }

    /**
     * Attaches click listeners and text watchers to UI components.
     */
    private void setupListeners(){

        //Watches the username field and checks it as the user types.
        usernameInput.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }


            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                presenter.onUsernameChanged(s.toString().trim());

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        passwordInput.addTextChangedListener(new TextWatcher() {


            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().isEmpty()) {
                    passwordInput.getBackground().clearColorFilter();
                    passwordInput.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                    passwordInput.setError(null);
                }


            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


        btnlogin.setOnClickListener(v -> {
            String user = usernameInput.getText().toString().trim();
            String password = passwordInput.getText().toString().trim();

            boolean hasError = false;

            // Perform  local validation for empty fields.
            if (user.isEmpty()) {
                usernameInput.setError("Username required");
                hasError = true;
            }
            if (password.isEmpty()) {
                passwordInput.setError("Password required");
                passwordInput.requestFocus();
                hasError = true;
            }
            //Validate inputs and pass the login task to the presenter.
            if (!hasError) {

                presenter.onLoginClick(user, password);
            }
        });

        btncancel.setOnClickListener(v -> {
            presenter.onCancelClick();
        });
    }

    @Override
    public void confirmLogin() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void confirmAdminLogin() {
        Intent intent = new Intent(this, AdminHomeActivity.class);
        startActivity(intent);
        finish();
    }

}
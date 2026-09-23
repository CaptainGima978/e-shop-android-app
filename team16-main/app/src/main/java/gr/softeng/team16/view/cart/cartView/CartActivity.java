package gr.softeng.team16.view.cart.cartView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Locale;

import gr.softeng.team16.R;
import gr.softeng.team16.data.FirebaseRepository;
import gr.softeng.team16.domain.Address;
import gr.softeng.team16.domain.Cart;
import gr.softeng.team16.domain.Order;
import gr.softeng.team16.view.cart.personalDetails.fillPersonalDetailsActivity;
import gr.softeng.team16.view.home.HomeActivity;

public class CartActivity extends AppCompatActivity implements CartView {

    private CartAdapter adapter;
    private TextView totalPriceText;
    private RecyclerView recyclerView;

    private CartViewModel viewModel;
    private CartPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cart);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize Presenter
        viewModel = new ViewModelProvider(this).get(CartViewModel.class);
        presenter = viewModel.getPresenter();
        presenter.setView(this);
        presenter.setRepo(FirebaseRepository.getInstance());

        totalPriceText = findViewById(R.id.total_price_text);
        recyclerView = findViewById(R.id.cart_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize empty adapter first
        adapter = new CartAdapter(new ArrayList<>(), new CartAdapter.Listener() {

            //Methods onIncrease, onDecrease and onRemove of the adapter are overriden and methods of the presenter are called for their implementetion

            @Override
            public void onIncrease(int position) {
                presenter.increaseQuantity(position);
            }

            @Override
            public void onDecrease(int position) {
                presenter.decreaseQuantity(position);
            }

            @Override
            public void onRemove(int position) {
                presenter.removeFromCart(position);
            }
        });
        recyclerView.setAdapter(adapter);

        // Buttons
        Button btnBackToHome = findViewById(R.id.btnBackToHome);
        if (btnBackToHome != null) {
            btnBackToHome.setOnClickListener(v -> {
                startActivity(new Intent(this, HomeActivity.class));
                finish();
            });
        }

        Button btnCheckout = findViewById(R.id.btnCheckout);
        if (btnCheckout != null) {
            btnCheckout.setOnClickListener(v -> {
                if (presenter.getCart().getProductList().isEmpty()) {
                    Toast.makeText(this, "Cart is empty!", Toast.LENGTH_SHORT).show();
                } else {
                    checkout();
                }
            });
        }

        // Trigger data load
        presenter.loadCart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.loadCart();
    }

    @Override
    public void showSuccess(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, "Error: " + message, Toast.LENGTH_SHORT).show();


    }

    @Override
    public void updateTotalPrice() {
        if (totalPriceText != null && presenter.getCart() != null) {
            totalPriceText.setText(String.format(Locale.US, "%.2f€", presenter.getCart().getTotalPrice()));
        }
    }

    @Override
    public void showCart() {
        if (adapter != null && presenter.getCart() != null) {
            adapter.updateCart(presenter.getCart().getProductList());
        }
    }

    private void checkout() {
        Intent intent = new Intent(this, fillPersonalDetailsActivity.class);
        startActivity(intent);
    }
}

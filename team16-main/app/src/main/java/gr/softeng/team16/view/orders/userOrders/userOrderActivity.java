package gr.softeng.team16.view.orders.userOrders;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import gr.softeng.team16.R;
import gr.softeng.team16.data.FirebaseRepository;
import gr.softeng.team16.domain.Order;
import gr.softeng.team16.view.cart.cartView.CartViewModel;

public class userOrderActivity extends AppCompatActivity implements userOrderView {

    private userOrderPresenter presenter;
    private RecyclerView recyclerView;
    private TextView noOrdersText;

    private userOrderViewModel viewModel;
    private OrderAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_order);

        recyclerView = findViewById(R.id.orders_recycler_view);
        noOrdersText = findViewById(R.id.no_orders_text);
        
        if (recyclerView != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }

        // Initialize Presenter
        viewModel = new ViewModelProvider(this).get(userOrderViewModel.class);
        presenter = viewModel.getPresenter();
        presenter.setView(this);
        presenter.setRepo(FirebaseRepository.getInstance());

        presenter.loadOrders();

        Button btnBack = findViewById(R.id.btnBack);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> presenter.onBackClick());
        }
    }

    @Override
    public void showOrders(List<Order> orders) {
        if (noOrdersText != null) noOrdersText.setVisibility(View.GONE);
        if (recyclerView != null) {
            recyclerView.setVisibility(View.VISIBLE);
            // Passing a listener to the adapter to handle removals
            adapter = new OrderAdapter(orders, order -> presenter.removeOrder(order));
            recyclerView.setAdapter(adapter);
        }
    }

    @Override
    public void showNoOrders() {
        if (recyclerView != null) recyclerView.setVisibility(View.GONE);
        if (noOrdersText != null) noOrdersText.setVisibility(View.VISIBLE);
    }

    @Override
    public void showSuccess(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void navigateBack() {
        finish();
    }
}

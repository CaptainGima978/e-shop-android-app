package gr.softeng.team16.view.orders.adminOrders;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import gr.softeng.team16.R;
import gr.softeng.team16.data.FirebaseRepository;
import gr.softeng.team16.domain.Order;

public class AdminOrdersActivity extends AppCompatActivity implements AdminOrdersView {

    private AdminOrdersViewModel viewModel;
    private AdminOrdersPresenter presenter;
    private AdminOrdersAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_orders);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageButton home = findViewById(R.id.btnAdminOrdersBack);
        home.setOnClickListener(v -> {
            finish();
        });

        viewModel = new ViewModelProvider(this).get(AdminOrdersViewModel.class);
        presenter = viewModel.getPresenter();
        presenter.setView(this);
        presenter.setRepository(FirebaseRepository.getInstance());

        RecyclerView recyclerView = findViewById(R.id.rvAdminOrders);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        
        adapter = new AdminOrdersAdapter(new AdminOrdersAdapter.OnOrderActionListener() {
            @Override
            public void onAcceptOrder(Order order) {
                presenter.acceptOrder(order);
            }
        });
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.loadOrders();
    }

    @Override
    public void showOrders(List<Order> result) {
        adapter.updateData(result);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
package gr.softeng.team16.view.home;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import gr.softeng.team16.MainActivity;
import gr.softeng.team16.R;
import gr.softeng.team16.data.FirebaseRepository;
import gr.softeng.team16.view.Login.LoginActivity;
import gr.softeng.team16.view.SavedBuilds.SavedBuildsActivity;
import gr.softeng.team16.view.build.BuildActivity;
import gr.softeng.team16.view.cart.cartView.CartPresenter;
import gr.softeng.team16.view.componentList.ComponentListActivity;
import gr.softeng.team16.view.offeredBuilds.OfferedBuildsActivity;
import gr.softeng.team16.view.orders.userOrders.userOrderActivity;
import gr.softeng.team16.view.cart.cartView.CartActivity;


public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        CartPresenter.getInstance().setRepo(FirebaseRepository.getInstance());
        ImageButton btnBuild = findViewById(R.id.btnBuild);
        btnBuild.setOnClickListener(v -> startActivity(new Intent(this, BuildActivity.class)));

        ImageButton btnBrowse = findViewById(R.id.btnBrowse);
        btnBrowse.setOnClickListener(v -> startActivity(new Intent(this, ComponentListActivity.class)));

        ImageButton btnSavedBuilds = findViewById(R.id.btnPCs);
        btnSavedBuilds.setOnClickListener(v->{
            startSavedBuildsActivity();
        });

        ImageButton btnOpenCart = findViewById(R.id.btnOpenCart);
        btnOpenCart.setOnClickListener(v -> {
            startActivity(new Intent(this, CartActivity.class));
        });

        ImageButton btnOfferedBuilds = findViewById(R.id.btnOfferedBuilds);
        btnOfferedBuilds.setOnClickListener(v -> {
            startOfferedBuildsActivity();
        });

        ImageButton btnMyOrders = findViewById(R.id.btnMyOrders);
        btnMyOrders.setOnClickListener(v -> {
            startActivity(new Intent(this, userOrderActivity.class));
        });


        ImageButton btnLogout = findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(v -> {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        });

    }

    private void startOfferedBuildsActivity() {
        Intent intent = new Intent(this, OfferedBuildsActivity.class);
        intent.putExtra("Admin", false);
        startActivity(intent);
    }

    private void startSavedBuildsActivity() {
        Intent intent = new Intent(this, SavedBuildsActivity.class);
        startActivity(intent);
    }

}

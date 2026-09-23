package gr.softeng.team16.view.adminHome;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import gr.softeng.team16.MainActivity;
import gr.softeng.team16.R;
import gr.softeng.team16.domain.*;
import gr.softeng.team16.data.FirebaseRepository;
import gr.softeng.team16.view.Login.LoginActivity;
import gr.softeng.team16.view.build.BuildActivity;
import gr.softeng.team16.view.componentList.ComponentListActivity;
import gr.softeng.team16.view.offeredBuilds.OfferedBuildsActivity;
import gr.softeng.team16.view.orders.adminOrders.AdminOrdersActivity;

public class AdminHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageButton btnBuild = findViewById(R.id.btnAdminBuild);
        btnBuild.setOnClickListener(v -> startBuildActivity());

        ImageButton btnBrowse = findViewById(R.id.btnAdminBrowse);
        btnBrowse.setOnClickListener(v -> startBrowseActivity());

        ImageButton btnOfferedBuilds = findViewById(R.id.btnAdminPCs);
        btnOfferedBuilds.setOnClickListener(v -> startOfferedBuildsActivity());


        ImageButton btnOrders = findViewById(R.id.btnOrders);
        btnOrders.setOnClickListener(v -> {
            startActivity(new Intent(this, AdminOrdersActivity.class));
        });

        ImageButton btnLogout = findViewById(R.id.btnLogoutAdmin);
        btnLogout.setOnClickListener(v -> {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        });
    }

    private void startOfferedBuildsActivity() {
        Intent intent = new Intent(this, OfferedBuildsActivity.class);
        intent.putExtra("Admin", true);
        startActivity(intent);
    }

    private void startBuildActivity() {
        Intent intent = new Intent(this, BuildActivity.class);
        intent.putExtra("Admin", true);
        startActivity(intent);
    }

    private void startBrowseActivity() {
        Intent intent = new Intent(this, ComponentListActivity.class);
        intent.putExtra("Admin", true);
        startActivity(intent);
    }

}

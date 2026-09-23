package gr.softeng.team16.view.offeredBuilds;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
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
import gr.softeng.team16.domain.Admin_Build;
import gr.softeng.team16.domain.Build;

public class OfferedBuildsActivity extends AppCompatActivity implements OfferedBuildsView {

    private OfferedBuildsViewModel viewModel;
    private OfferedBuildsAdapter adapter;
    private boolean isAdmin;
    private OfferedBuildsPresenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_offered_builds);
        isAdmin = getIntent().getBooleanExtra("Admin", false);


        viewModel = new ViewModelProvider(this).get(OfferedBuildsViewModel.class);
        
        presenter = viewModel.getPresenter();
        presenter.setView(this);
        presenter.setRepo(FirebaseRepository.getInstance());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        RecyclerView recyclerView = findViewById(R.id.recyclerViewOfferedBuilds);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new OfferedBuildsAdapter( new OfferedBuildsAdapter.BuildItemListener() {
            @Override
            public void onOpenInBuilder(Build build) {
                checkOpenInBuilder(build);
            }

            @Override
            public void onAddOrRemove(Build build) {
                if (isAdmin) {
                    checkRemoveBuild(build);
                }
                else {
                    presenter.addToCart(build);
                }
            }
        }, isAdmin);
        recyclerView.setAdapter(adapter);
        presenter.loadBuilds();

        findViewById(R.id.btnExit).setOnClickListener(v -> finish());
    }
    
    public void checkOpenInBuilder(Build build) {
        new AlertDialog.Builder(this)
                .setTitle("Open In Builder")
                .setMessage("Are you sure you want to open this build in the builder? Any unsaved changes in your current build will be lost.")
                .setPositiveButton("Yes", (dialog, which) -> presenter.openInBuilder(build))
                .setNegativeButton("No", null)
                .show();
    }

    public void checkRemoveBuild(Build build) {
        new AlertDialog.Builder(this)
                .setTitle("Remove Build")
                .setMessage("Are you sure you want to remove this build?")
                .setPositiveButton("Yes", (dialog, which) -> presenter.removeBuild(build))
                .setNegativeButton("No", null)
                .show();
    }


    @Override
    public void showSuccess(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    public void loadBuilds(List<Admin_Build> builds) {
        adapter.setBuilds(builds);
    }
}
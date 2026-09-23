package gr.softeng.team16.view.SavedBuilds;

import android.os.Bundle;
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
import gr.softeng.team16.domain.Build;

/**
 * Activity to display saved builds
 */

public class SavedBuildsActivity extends AppCompatActivity implements SavedBuildsView{

    private SavedBuildsPresenter presenter;
    private SavedBuildsAdapter adapter;
    private SavedBuildsViewModel viewModel;

    /**
     * Initializes the activity, sets up edge-to-edge display, RecyclerView, and presenter.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_saved_builds);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        RecyclerView recyclerView = findViewById(R.id.saved_builds_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new SavedBuildsAdapter(new SavedBuildsAdapter.OnBuildDeleteListener() {
            @Override
            public void onBuildDelete(Build build) {
                presenter.deleteBuild(build);
            }

            @Override
            public void onBuildAddToCart(Build build) {
                presenter.addToCart(build);
            }
        });
        recyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(SavedBuildsViewModel.class);

        presenter = viewModel.getPresenter();
        presenter.setView(this);

        findViewById(R.id.btnBack).setOnClickListener(v -> {
            finish();
        });

        presenter.loadBuilds();


    }


    /**
     * Displays the list of saved builds in the RecyclerView.
     * @param builds List of saved builds to display.
     */
    @Override
    public void displaySavedBuilds(List<Build> builds) {
        adapter.setBuilds(builds);

    }
    /**
     * Shows an error message as a Toast.
     * @param message The error message to display.
     */
    @Override
    public void showError(String message) {
        Toast.makeText(this, "Error: " + message, Toast.LENGTH_SHORT).show();


    }

    /**
     * Shows a message indicating that there are no saved builds.
     */
    @Override
    public void showEmptyMessage() {
        Toast.makeText(this, "No saved builds found", Toast.LENGTH_SHORT).show();

    }

    /**
     * Handles the event when a build is deleted.
     * @param build The build that was deleted.
     */
    @Override
    public void onBuildDeleted(Build build) {
        Toast.makeText(this, "Build deleted successfully", Toast.LENGTH_SHORT).show();
        adapter.removeBuild(build);

    }





}

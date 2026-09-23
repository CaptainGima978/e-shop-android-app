package gr.softeng.team16.view.componentList;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;
import java.util.List;

import gr.softeng.team16.R;
import gr.softeng.team16.data.FirebaseRepository;
import gr.softeng.team16.data.LoadCallback;
import gr.softeng.team16.domain.Component;
import gr.softeng.team16.view.componentEditor.ComponentEditorActivity;

public class ComponentListActivity extends AppCompatActivity implements ComponentListView {

    private ComponentListViewModel viewModel;
    private ComponentListPresenter presenter;
    private FirebaseRepository repo;
    private boolean isAdmin;

    private ComponentAdapter adapter;;
    private String currentCategory;
    private Class<? extends Component> categoryClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_component_list);

        repo = FirebaseRepository.getInstance();

        isAdmin = getIntent().getBooleanExtra("Admin", false);

        viewModel = new ViewModelProvider(this).get(ComponentListViewModel.class);
        presenter = viewModel.getPresenter();
        presenter.setView(this);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        RecyclerView recyclerView = findViewById(R.id.component_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new ComponentAdapter(new ComponentAdapter.Listener() {
            @Override
            public void onAddToBuild(Component component) {
                presenter.addComponentToBuild(component);
            }

            @Override
            public void onAddToCart(Component component) {
                presenter.addComponentToCart(component);
            }

            @Override
            public void onEdit(Component component) {
                presenter.sendToEditor(component);
                Intent intent = new Intent(ComponentListActivity.this, ComponentEditorActivity.class);
                startActivity(intent);
            }

        }, isAdmin);

        recyclerView.setAdapter(adapter);

        EditText searchBar = findViewById(R.id.search_bar);
        if (searchBar != null) {
            searchBar.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    adapter.filter(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {}
            });
        }

        setUpCategoryButtons();

        Button btnNewComponent = findViewById(R.id.btnNewComponent);
        if (btnNewComponent != null) {
            btnNewComponent.setVisibility(isAdmin ? View.VISIBLE : View.GONE);
            btnNewComponent.setOnClickListener(v -> {
                Toast.makeText(this, "Not yet implemented :(", Toast.LENGTH_SHORT).show();
            });
        }

        presenter.loadComponents("Tower");
    }

    private void setUpCategoryButtons() {
        findViewById(R.id.category_tower).setOnClickListener(v -> presenter.loadComponents("Tower"));
        findViewById(R.id.category_motherboard).setOnClickListener(v -> presenter.loadComponents("Motherboard"));
        findViewById(R.id.category_cpu).setOnClickListener(v -> presenter.loadComponents("CPU"));
        findViewById(R.id.category_gpu).setOnClickListener(v -> presenter.loadComponents("GPU"));
        findViewById(R.id.category_memory).setOnClickListener(v -> presenter.loadComponents("Memory"));
        findViewById(R.id.category_storage).setOnClickListener(v -> presenter.loadComponents("Storage"));
        findViewById(R.id.category_psu).setOnClickListener(v -> presenter.loadComponents("PSU"));
        findViewById(R.id.category_cooler).setOnClickListener(v -> presenter.loadComponents("Cooler"));

        findViewById(R.id.btnOkCompSelect).setOnClickListener(v -> presenter.completeComponentSelection());
    }

    @Override
    public void showComponents() {
        repo.getComponents(currentCategory, categoryClass, new LoadCallback<List<? extends Component>>() {
            @Override
            public void onLoaded(List<? extends Component> result) {
                adapter.submitList(new ArrayList<>(result));
            }

            @Override
            public void onError(DatabaseError error) {
            }
        });
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
    public void closeWithSuccess() {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void closeWithCancel() {
        setResult(RESULT_CANCELED);
        finish();
    }

    @Override
    public void setCategory(String category) {
        currentCategory = category;
    }


    @Override
    public void setCategoryClass(Class<? extends Component> clazz) {
        categoryClass = clazz;
    }
}

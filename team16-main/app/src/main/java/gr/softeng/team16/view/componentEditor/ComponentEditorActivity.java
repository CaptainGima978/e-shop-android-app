package gr.softeng.team16.view.componentEditor;

import android.health.connect.datatypes.PowerRecord;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import gr.softeng.team16.R;
import gr.softeng.team16.data.FirebaseRepository;

public class ComponentEditorActivity extends AppCompatActivity implements ComponentEditorView {

    private ComponentEditorPresenter presenter;
    private ComponentEditorViewModel viewModel;
    private ComponentFieldsAdapter adapter;
    private final List<ComponentFieldsAdapter.FieldData> fields = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_component_editor);

        viewModel = new ViewModelProvider(this).get(ComponentEditorViewModel.class);
        presenter = viewModel.getPresenter();
        presenter.setView(this);
        presenter.setRepo(FirebaseRepository.getInstance());

        setupRecyclerView();
        loadFields();

        Button save = findViewById(R.id.btnSaveComponent);
        save.setOnClickListener(v -> saveComponent());

        Button cancel = findViewById(R.id.btnCancelEdit);
        cancel.setOnClickListener(v -> cancelEdit());

        Button delete = findViewById(R.id.btnDeleteComponent);
        delete.setOnClickListener(v -> deleteComponent());

    }

    private void setupRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.component_fields_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ComponentFieldsAdapter(fields);
        recyclerView.setAdapter(adapter);

        adapter.setOnFieldAppliedListener(new ComponentFieldsAdapter.OnFieldAppliedListener() {
            @Override
            public void onFieldApplied(int position, String fieldName, String newValue) {
                fields.get(position).setFieldValue(newValue);
                presenter = viewModel.getPresenter();
                if (presenter.updateField(fieldName, newValue)) {
                    RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForAdapterPosition(position);
                    if (viewHolder != null) {
                        com.google.android.material.textfield.TextInputLayout textInputLayout = viewHolder.itemView.findViewById(R.id.field_text_layout);
                        if (textInputLayout != null) {
                            textInputLayout.setBoxStrokeColor(getColor(android.R.color.holo_green_dark));
                        }
                    }
                }
                adapter.notifyItemChanged(position);
            }
        });
    }

    @Override
    public void loadFields() {
        presenter = viewModel.getPresenter();
        presenter.loadComponent();
    }

    @Override
    public void showField(String fieldName, String fieldValue) {
        fields.add(new ComponentFieldsAdapter.FieldData(fieldName, fieldValue));
        adapter.notifyDataSetChanged();
    }

    @Override
    public void setID(String id) {
        TextView textView = findViewById(R.id.component_id_header);
        textView.setText(id);
    }

    @Override
    public void setName(String name) {
        TextView textView = findViewById(R.id.component_header);
        textView.setText(name);
    }

    @Override
    public void saveComponent() {
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to commit the changes?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    presenter = viewModel.getPresenter();
                    presenter.saveComponent();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    @Override
    public void deleteComponent() {
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to delete the component?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    presenter = viewModel.getPresenter();
                    presenter.deleteComponent();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    @Override
    public void cancelEdit() {
        presenter = viewModel.getPresenter();
        presenter.cancelEdit();
    }

    @Override
    public void closeWithCancel() {
        Toast.makeText(this, "Edit canceled", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void closeWithSuccess() {
        Toast.makeText(this, "Changes saved", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void closeWithDeletion() {
        Toast.makeText(this, "Component deleted", Toast.LENGTH_SHORT).show();
        finish();
    }


}

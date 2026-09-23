package gr.softeng.team16.view.componentEditor;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

import gr.softeng.team16.R;

public class ComponentFieldsAdapter extends RecyclerView.Adapter<ComponentFieldsAdapter.FieldViewHolder> {

    private final List<FieldData> fields;
    private OnFieldAppliedListener onFieldAppliedListener;

    public interface OnFieldAppliedListener {
        void onFieldApplied(int position, String fieldName, String newValue);
    }

    public void setOnFieldAppliedListener(OnFieldAppliedListener listener) {
        this.onFieldAppliedListener = listener;
    }

    public static class FieldData {
        private final String fieldName;
        private String fieldValue;

        public FieldData(String fieldName, String fieldValue) {
            this.fieldName = fieldName;
            this.fieldValue = fieldValue;
        }

        public String getFieldName() {
            return fieldName;
        }

        public String getFieldValue() {
            return fieldValue;
        }

        public void setFieldValue(String fieldValue) {
            this.fieldValue = fieldValue;
        }
    }

    public ComponentFieldsAdapter(List<FieldData> fields) {
        this.fields = fields;
    }

    @NonNull
    @Override
    public FieldViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.component_field_item, parent, false);
        return new FieldViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FieldViewHolder holder, int position) {
        FieldData currentField = fields.get(position);
        holder.fieldName.setText(currentField.getFieldName());
        holder.fieldValue.setText(currentField.getFieldValue());

        holder.fabAction.setOnClickListener(v -> {
            if (onFieldAppliedListener != null) {
                String newValue = holder.fieldValue.getText().toString();
                currentField.setFieldValue(newValue);
                onFieldAppliedListener.onFieldApplied(holder.getBindingAdapterPosition(), currentField.getFieldName(), newValue);
            }
        });
    }

    @Override
    public int getItemCount() {
        return fields.size();
    }

    public static class FieldViewHolder extends RecyclerView.ViewHolder {
        TextView fieldName;
        TextInputEditText fieldValue;
        FloatingActionButton fabAction;

        public FieldViewHolder(@NonNull View itemView) {
            super(itemView);
            fieldName = itemView.findViewById(R.id.field_label);
            fieldValue = itemView.findViewById(R.id.field_edit_text);
            fabAction = itemView.findViewById(R.id.btnApply);
        }
    }
}

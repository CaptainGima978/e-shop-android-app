package gr.softeng.team16.view.componentEditor;

import java.util.HashMap;
import java.util.Map;

public class ComponentEditViewStub implements ComponentEditorView {
    
    private String name;
    private String id;
    private final Map<String, String> fields = new HashMap<>();
    
    private boolean closedWithCancel = false;
    private boolean closedWithSuccess = false;
    private boolean closedWithDeletion = false;

    @Override
    public void loadFields() {
        // No-op for stub
    }

    @Override
    public void showField(String fieldName, String fieldValue) {
        fields.put(fieldName, fieldValue);
    }

    @Override
    public void setID(String id) {
        this.id = id;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void saveComponent() {
        // No-op for stub
    }

    @Override
    public void deleteComponent() {
        // No-op for stub
    }

    @Override
    public void cancelEdit() {
        // No-op for stub
    }

    @Override
    public void closeWithCancel() {
        closedWithCancel = true;
    }

    @Override
    public void closeWithSuccess() {
        closedWithSuccess = true;
    }

    @Override
    public void closeWithDeletion() {
        closedWithDeletion = true;
    }

    // --- Helper methods for testing ---

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getFieldValue(String fieldName) {
        return fields.get(fieldName);
    }

    public boolean isClosedWithCancel() {
        return closedWithCancel;
    }

    public boolean isClosedWithSuccess() {
        return closedWithSuccess;
    }

    public boolean isClosedWithDeletion() {
        return closedWithDeletion;
    }
}

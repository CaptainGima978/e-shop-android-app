package gr.softeng.team16.view.componentEditor;

public interface ComponentEditorView {

    void loadFields();
    void showField(String fieldName, String fieldValue);
    void setID(String id);
    void setName(String name);

    void saveComponent();

    void deleteComponent();

    void cancelEdit();

    void closeWithCancel();
    void closeWithSuccess();
    void closeWithDeletion();
}

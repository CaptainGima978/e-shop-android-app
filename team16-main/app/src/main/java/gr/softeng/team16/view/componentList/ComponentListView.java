package gr.softeng.team16.view.componentList;

import java.util.List;

import gr.softeng.team16.domain.Component;

public interface ComponentListView {

    /**
     * Displays a list of components in the UI.
     */
    void showComponents();

    /**
     * Shows a success message to the user (e.g., a Toast).
     * @param message The message to display.
     */
    void showSuccess(String message);

    /**
     * Shows an error message to the user (e.g., a Toast).
     * @param message The error message to display.
     */
    void showError(String message);

    /**
     * Closes the component selection screen with a success result.
     */
    void closeWithSuccess();

    /**
     * Closes the component selection screen with a cancel result.
     */
    void closeWithCancel();

    /**
     * Sets the currently selected category.
     * @param category The name of the category.
     */
    void setCategory(String category);

    void setCategoryClass(Class<? extends Component> clazz);
}

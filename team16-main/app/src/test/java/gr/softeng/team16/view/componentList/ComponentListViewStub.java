package gr.softeng.team16.view.componentList;

import java.util.ArrayList;
import java.util.List;

import gr.softeng.team16.domain.Component;

public class ComponentListViewStub implements ComponentListView {

    private String category;
    private Class<? extends Component> categoryClass;
    private String successMessage;
    private String errorMessage;
    private boolean closedWithSuccess = false;
    private boolean closedWithCancel = false;
    private boolean showComponentsCalled = false;

    @Override
    public void showComponents() {
        showComponentsCalled = true;
    }

    @Override
    public void showSuccess(String message) {
        this.successMessage = message;
    }

    @Override
    public void showError(String message) {
        this.errorMessage = message;
    }

    @Override
    public void closeWithSuccess() {
        this.closedWithSuccess = true;
    }

    @Override
    public void closeWithCancel() {
        this.closedWithCancel = true;
    }

    @Override
    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public void setCategoryClass(Class<? extends Component> clazz) {
        this.categoryClass = clazz;
    }

    // --- Helper methods for testing ---

    public String getCategory() {
        return category;
    }

    public Class<? extends Component> getCategoryClass() {
        return categoryClass;
    }

    public String getSuccessMessage() {
        return successMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public boolean isClosedWithSuccess() {
        return closedWithSuccess;
    }

    public boolean isClosedWithCancel() {
        return closedWithCancel;
    }

    public boolean isShowComponentsCalled() {
        return showComponentsCalled;
    }
}

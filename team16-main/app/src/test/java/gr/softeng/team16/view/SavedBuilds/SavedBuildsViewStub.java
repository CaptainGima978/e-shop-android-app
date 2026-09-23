package gr.softeng.team16.view.SavedBuilds;

import java.util.List;

import gr.softeng.team16.domain.Build;

public class SavedBuildsViewStub implements SavedBuildsView{
    public boolean displaySavedBuildsCalled = false;
    public boolean onBuildDeletedCalled = false;
    public boolean showEmptyMessageCalled = false;
    public int errorCount = 0;
    public String errorMessage = "";

    public Build deleteBuild;
    @Override
    public void displaySavedBuilds(List<Build> builds) {
        displaySavedBuildsCalled = true;

    }

    @Override
    public void showError(String message) {
        errorCount++;
        errorMessage = message;

    }

    @Override
    public void showEmptyMessage() {
        showEmptyMessageCalled = true;

    }

    @Override
    public void onBuildDeleted(Build build) {
        onBuildDeletedCalled = true;
        deleteBuild = build;

    }
}

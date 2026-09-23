package gr.softeng.team16.view.SavedBuilds;

import java.util.List;

import gr.softeng.team16.domain.Build;

/**
 * Interface representing the view for saved builds.
 */
public interface SavedBuildsView {
    /**
     * Displays the list of saved builds.
     * @param builds List of saved builds to display.
     */
    void displaySavedBuilds(List<Build> builds);
    /**
     * Shows an error message.
     * @param message The error message to display.
     */
    void showError(String message);
    /**
     * Shows a message indicating that there are no saved builds.
     */
    void showEmptyMessage();

    /**
     * Callback method when a build is deleted.
     * @param build The build that was deleted.
     */
    void onBuildDeleted(Build build);

}

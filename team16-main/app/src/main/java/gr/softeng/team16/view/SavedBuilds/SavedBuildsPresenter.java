package gr.softeng.team16.view.SavedBuilds;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import gr.softeng.team16.data.AuthCallback;
import gr.softeng.team16.data.DataRepository;
import gr.softeng.team16.data.LoadCallback;
import gr.softeng.team16.domain.Build;
import gr.softeng.team16.domain.OrderLine;
import gr.softeng.team16.domain.Product;
import gr.softeng.team16.view.cart.cartView.CartPresenter;

/**
 * Presenter for the Saved Builds view.
 * Handles loading and deleting saved builds from Firebase.
 */
public class SavedBuildsPresenter {
    private SavedBuildsView view;
    private final DataRepository repository;

    /**
     * Constructor initializing the presenter with a Firebase DatabaseReference.
     * @param repository Reference to the Firebase database location for saved builds.
     */
    public SavedBuildsPresenter(DataRepository repository) {
        this.repository = repository;


    }

    public void setView(SavedBuildsView view){
        this.view = view;
    }

    /**
     * Loads saved builds from Firebase and updates the view.
     */
    public void loadBuilds(){
        repository.getSavedBuilds(new LoadCallback<List<Build>>() {
            @Override
            public void onLoaded(List<Build> builds) {
                if (view == null) return;

                if (builds.isEmpty()) {
                    view.showEmptyMessage();
                } else {
                    view.displaySavedBuilds(builds);
                }
            }

            @Override
            public void onError(DatabaseError error) {
                if (view != null) {

                    String message = (error != null) ? error.getMessage() : "Unknown database error";
                    view.showError(message);
                }
            }
        });
    }
    /**
     * Deletes a build from Firebase and updates the view.
     * @param build The build to be deleted.
     */
    public void deleteBuild(Build build){
        if (build.getFirebaseKey() == null) return;

        repository.deleteBuild(build.getFirebaseKey(), new AuthCallback() {
            @Override
            public void onSuccess(String message) {
                if (view != null) {
                    view.onBuildDeleted(build);
                }
            }

            @Override
            public void onFailure(String message) {
                if (view != null) {
                    view.showError("Delete failed: " + message);
                }
            }
        });
    }


    public void addToCart(Build build) {
        CartPresenter.getInstance().addToCart(new OrderLine(new Product(build)));
        view.showError("Product added to cart");
    }
}


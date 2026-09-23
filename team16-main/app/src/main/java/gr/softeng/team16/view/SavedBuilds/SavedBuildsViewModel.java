package gr.softeng.team16.view.SavedBuilds;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import gr.softeng.team16.data.FirebaseRepository;

/**
 * ViewModel for the Saved Builds screen.
 * Manages the data for saved builds using Firebase.
 */
public class SavedBuildsViewModel extends AndroidViewModel {

    private DatabaseReference buildsRef;

    private SavedBuildsPresenter presenter;

    /**
     * Constructor initializing the ViewModel with application context.
     * Sets up the Firebase DatabaseReference for the user's saved builds.
     * @param application Application context.
     */
    public SavedBuildsViewModel(@NonNull Application application) {
        super(application);

        String uid = FirebaseAuth.getInstance().getUid();

        if (uid != null) {
            buildsRef = FirebaseDatabase.getInstance().getReference("users").child(uid).child("saved_builds");
        }
        presenter = new SavedBuildsPresenter(FirebaseRepository.getInstance());

    }
    public DatabaseReference getBuildsRef() {
        return buildsRef;
    }

    public SavedBuildsPresenter getPresenter() {
        return presenter;
    }


}

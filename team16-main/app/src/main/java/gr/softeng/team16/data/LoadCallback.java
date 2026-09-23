package gr.softeng.team16.data;

import com.google.firebase.database.DatabaseError;

public interface LoadCallback<T> {
    void onLoaded(T result);
    void onError(DatabaseError error);
}

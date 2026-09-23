package gr.softeng.team16.view.offeredBuilds;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class OfferedBuildsViewModel extends AndroidViewModel {

    OfferedBuildsPresenter presenter;

    public OfferedBuildsViewModel(@NonNull Application application) {
        super(application);
        this.presenter = OfferedBuildsPresenter.getInstance();
    }

    public OfferedBuildsPresenter getPresenter() {
        return presenter;
    }

}

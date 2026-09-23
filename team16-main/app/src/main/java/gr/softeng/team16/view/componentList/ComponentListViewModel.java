package gr.softeng.team16.view.componentList;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class ComponentListViewModel extends AndroidViewModel {

    private final ComponentListPresenter presenter;

    public ComponentListViewModel(@NonNull Application application) {
        super(application);
        this.presenter = new ComponentListPresenter();
    }

    public ComponentListPresenter getPresenter() {
        return presenter;
    }

}
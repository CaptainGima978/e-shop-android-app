package gr.softeng.team16.view.componentEditor;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class ComponentEditorViewModel extends AndroidViewModel {

    private ComponentEditorPresenter presenter;

    public ComponentEditorViewModel(@NonNull Application application) {
        super(application);
        this.presenter = ComponentEditorPresenter.getInstance();
    }

    public ComponentEditorPresenter getPresenter() {
        return presenter;
    }
}

package gr.softeng.team16.view.orders.adminOrders;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import gr.softeng.team16.data.FirebaseRepository;

public class AdminOrdersViewModel extends AndroidViewModel {

    private AdminOrdersPresenter presenter;


    public AdminOrdersViewModel(@NonNull Application application) {
        super(application);
        presenter = new AdminOrdersPresenter();
    }


    public AdminOrdersPresenter getPresenter() {
        if (presenter == null) {
            presenter = new AdminOrdersPresenter();
        }
        return presenter;
    }
}

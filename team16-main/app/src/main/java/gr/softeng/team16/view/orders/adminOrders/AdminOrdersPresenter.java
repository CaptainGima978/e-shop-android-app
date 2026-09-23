package gr.softeng.team16.view.orders.adminOrders;

import com.google.firebase.database.DatabaseError;

import java.util.List;

import gr.softeng.team16.data.DataRepository;
import gr.softeng.team16.data.LoadCallback;
import gr.softeng.team16.domain.Order;

public class AdminOrdersPresenter {

    private AdminOrdersView view;
    private DataRepository repo;

    public AdminOrdersPresenter() {}

    public void loadOrders() {
        if (repo == null) return;
        if (view == null) return;
        repo.getOrders(new LoadCallback<List<Order>>() {
            @Override
            public void onLoaded(List<Order> result) {
                view.showOrders(result);
            }

            @Override
            public void onError(DatabaseError error) {
                String message = (error != null) ? error.getMessage() : "Database Error";
                view.showError(message);
            }
        });
    }

    public void setView(AdminOrdersView view) {
        this.view = view;
    }

    public void setRepository(DataRepository repository) {
        this.repo = repository;
    }

    public void showOrders() {
        repo.getOrders(new LoadCallback<List<Order>>() {
            @Override
            public void onLoaded(List<Order> result) {
                view.showOrders(result);
            }

            @Override
            public void onError(DatabaseError error) {

            }
        });
    }

    public void acceptOrder(Order order) {
        repo.acceptOrder(order);
        loadOrders();
    }

}

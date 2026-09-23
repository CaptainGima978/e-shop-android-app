package gr.softeng.team16.view.orders.userOrders;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import gr.softeng.team16.data.DataRepository;
import gr.softeng.team16.data.FirebaseRepository;
import gr.softeng.team16.domain.Order;

public class userOrderPresenter {
    private static userOrderPresenter instance;
    private userOrderView view;

    private DataRepository repo;

    public userOrderPresenter() {
    }

    public void setView(userOrderView view) {
        this.view = view;
    }
    public void setRepo(DataRepository repo) {
        this.repo = repo;
    }

    public static userOrderPresenter getInstance() {
        if (instance == null) {
            instance = new userOrderPresenter();
        }
        return instance;
    }

    public void loadOrders() {
        if (repo == null) return;

        String userId = repo.getUid();
        if (userId == null) {
            if (view != null) view.showNoOrders();
            return;
        }

        DatabaseReference ordersRef = FirebaseDatabase.getInstance().getReference("users")
                .child(userId)
                .child("orders");

        ordersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                List<Order> orders = new ArrayList<>();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Order order = ds.getValue(Order.class);
                    if (order != null) {
                        order.setFirebaseKey(ds.getKey()); // Store key for deletion
                        orders.add(order);
                    }
                }

                if (view != null) {
                    if (orders.isEmpty()) {
                        view.showNoOrders();
                    } else {
                        view.showOrders(orders);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.e("userOrderPresenter", "Error: " + error.getMessage());
            }
        });
    }

    public void removeOrder(Order order) {
        String userId = repo.getUid();
        if (userId == null || order.getFirebaseKey() == null) {
            if (view != null) view.showError("Could not remove order.");
            return;
        }

        DatabaseReference orderRef = FirebaseDatabase.getInstance().getReference("users")
                .child(userId)
                .child("orders")
                .child(order.getFirebaseKey());

        orderRef.removeValue().addOnCompleteListener(task -> {
            if (view != null) {
                if (task.isSuccessful()) {
                    view.showSuccess("Order removed.");
                } else {
                    view.showError("Failed to remove order.");
                }
            }
        });
    }

    public void onBackClick() {
        if (view != null) view.navigateBack();
    }
}


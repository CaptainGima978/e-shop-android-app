package gr.softeng.team16.view.orders.userOrders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Locale;

import gr.softeng.team16.R;
import gr.softeng.team16.domain.Order;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    public interface OnRemoveClickListener {
        void onRemove(Order order);
    }

    private final List<Order> orders;
    private final OnRemoveClickListener removeListener;

    public OrderAdapter(List<Order> orders, OnRemoveClickListener removeListener) {
        this.orders = orders;
        this.removeListener = removeListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_order, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Order order = orders.get(position);
        
        holder.tvOrderTag.setText(String.format(Locale.US, "My Order %d", position + 1));
        holder.tvOrderStatus.setText(order.getStatus());
        holder.tvOrderTotal.setText(String.format(Locale.US, "Total: %.2f€", order.getTotalPrice()));
        holder.tvOrderDetails.setText(String.format(Locale.US, "Total items: %d", order.getNumOfProducts()));

        holder.btnRemove.setOnClickListener(v -> {
            if (removeListener != null) {
                removeListener.onRemove(order);
            }
        });
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvOrderTag, tvOrderStatus, tvOrderDetails, tvOrderTotal;
        Button btnRemove;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvOrderTag = itemView.findViewById(R.id.tvOrderTag);
            tvOrderStatus = itemView.findViewById(R.id.tvOrderStatus);
            tvOrderDetails = itemView.findViewById(R.id.tvOrderDetails);
            tvOrderTotal = itemView.findViewById(R.id.tvOrderTotal);
            btnRemove = itemView.findViewById(R.id.btnRemoveOrder);
        }
    }
}

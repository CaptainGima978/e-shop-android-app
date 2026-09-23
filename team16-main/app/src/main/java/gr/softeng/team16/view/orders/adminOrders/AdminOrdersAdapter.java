package gr.softeng.team16.view.orders.adminOrders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import gr.softeng.team16.R;
import gr.softeng.team16.domain.Order;
import gr.softeng.team16.domain.OrderLine;

public class AdminOrdersAdapter extends RecyclerView.Adapter<AdminOrdersAdapter.ViewHolder> {

    private List<Order> orders;
    private OnOrderActionListener listener;

    public interface OnOrderActionListener {
        void onAcceptOrder(Order order);
    }

    public AdminOrdersAdapter( OnOrderActionListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_admin_order, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Order order = orders.get(position);
        holder.tvOrderTag.setText("Order #" + order.getOrderId());
        holder.tvStatus.setText(order.getStatus());
        
        if (order.getCustomerAccount() != null) {
            holder.tvCustomer.setText("Customer: " + order.getCustomerAccount().getName());
        } else {
            holder.tvCustomer.setText("Customer: Unknown");
        }

        StringBuilder details = new StringBuilder("Items: ");
        if (order.getLines() != null) {
            for (int i = 0; i < order.getLines().size(); i++) {
                OrderLine line = order.getLines().get(i);
                details.append(line.getProduct().getName());
                if (i < order.getLines().size() - 1) {
                    details.append(",\n");
                }
            }
        }
        holder.tvDetails.setText(details.toString());
        holder.tvTotal.setText(String.format("Total: %.2f€", order.getTotalPrice()));

        // Logic for the Accept button - show only if PENDING APPROVAL
        if ("PENDING ORDER".equals(order.getStatus())) {
            holder.tvStatus.setTextColor(holder.itemView.getResources().getColor(R.color.errorColor));
            holder.btnAccept.setVisibility(View.VISIBLE);
            holder.btnAccept.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onAcceptOrder(order);
                }
            });
        } else {
            holder.btnAccept.setVisibility(View.GONE);
            holder.tvStatus.setTextColor(holder.itemView.getResources().getColor(R.color.green));
        }
    }

    @Override
    public int getItemCount() {
        return orders != null ? orders.size() : 0;
    }

    public void updateData(List<Order> newOrders) {
        this.orders = newOrders;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvOrderTag, tvStatus, tvCustomer, tvDetails, tvTotal;
        Button btnAccept;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvOrderTag = itemView.findViewById(R.id.tvAdminOrderTag);
            tvStatus = itemView.findViewById(R.id.tvAdminOrderStatus);
            tvCustomer = itemView.findViewById(R.id.tvAdminOrderCustomer);
            tvDetails = itemView.findViewById(R.id.tvAdminOrderDetails);
            tvTotal = itemView.findViewById(R.id.tvAdminOrderTotal);
            btnAccept = itemView.findViewById(R.id.btnAcceptOrder);
        }
    }
}

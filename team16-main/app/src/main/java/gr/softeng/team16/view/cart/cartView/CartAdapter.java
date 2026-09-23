package gr.softeng.team16.view.cart.cartView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;
import java.util.Locale;

import gr.softeng.team16.R;
import gr.softeng.team16.domain.OrderLine;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    public interface Listener {
        void onIncrease(int position);
        void onDecrease(int position);
        void onRemove(int position);
    }

    private final List<OrderLine> items;
    private final Listener listener;

    public CartAdapter(List<OrderLine> items, Listener listener) {
        this.items = items;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OrderLine item = items.get(position);
        
        holder.productName.setText(item.getProduct().getName());
        holder.productPrice.setText(String.format(Locale.US, "%.2f€", item.getProduct().getPrice()));
        holder.tvQuantity.setText(String.valueOf(item.getQuantity()));

        // Load image using Glide if product has one
        String imageUrl = item.getProduct().getImg();
        Glide.with(holder.productImage.getContext())
                .load(imageUrl)
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground)
                .into(holder.productImage);

        holder.btnIncrease.setOnClickListener(v -> listener.onIncrease(holder.getAdapterPosition()));
        holder.btnDecrease.setOnClickListener(v -> listener.onDecrease(holder.getAdapterPosition()));
        holder.btnRemove.setOnClickListener(v -> listener.onRemove(holder.getAdapterPosition()));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView productName, productPrice, tvQuantity;
        ImageButton btnIncrease, btnDecrease, btnRemove;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.product_image);
            productName = itemView.findViewById(R.id.product_name);
            productPrice = itemView.findViewById(R.id.product_price);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
            btnIncrease = itemView.findViewById(R.id.btnIncrease);
            btnDecrease = itemView.findViewById(R.id.btnDecrease);
            btnRemove = itemView.findViewById(R.id.btnRemove);
        }
    }

    public void updateCart(List <OrderLine> newCartItems){
        items.clear();
        items.addAll(newCartItems);
        notifyDataSetChanged();
    }
}

// Note: CartAdapter is needed to represent all the items in the cart as cart_items (same for all the adapters used)
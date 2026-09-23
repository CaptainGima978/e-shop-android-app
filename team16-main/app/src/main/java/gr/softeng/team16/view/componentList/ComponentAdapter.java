package gr.softeng.team16.view.componentList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import gr.softeng.team16.R;
import gr.softeng.team16.domain.Component;

public class ComponentAdapter extends RecyclerView.Adapter<ComponentAdapter.VH> {

    public interface Listener {
        void onAddToBuild(Component component);
        void onAddToCart(Component component);
        void onEdit(Component component);
    }

    private List<Component> components = new ArrayList<>();
    private List<Component> componentsFull = new ArrayList<>();
    private final Listener listener;
    private boolean isAdmin;

    public ComponentAdapter(Listener listener, boolean isAdmin) {
        this.listener = listener;
        this.isAdmin = isAdmin;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.component_list_item, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        Component component = components.get(position);
        holder.name.setText(component.getName());
        holder.description.setText(component.toString());
        String priceStr = String.valueOf(component.getPrice()) + "€";
        holder.price.setText(priceStr);
        holder.idView.setText(String.valueOf(component.getId()));
        
        //load image
        String url = component.getImg();
        Glide.with(holder.image.getContext()).load(url)
                        .placeholder(R.drawable.ic_launcher_foreground)
                        .error(R.drawable.ic_launcher_foreground)
                        .into(holder.image);

        holder.buildAddButton.setOnClickListener(v -> listener.onAddToBuild(component));
        if (this.isAdmin) {
            holder.cartAddButton.setText(" Edit ");
            holder.cartAddButton.setOnClickListener(v -> listener.onEdit(component));
        }
        else {
            holder.cartAddButton.setOnClickListener(v -> listener.onAddToCart(component));
        }
    }

    @Override
    public int getItemCount() {
        return components.size();
    }

    public void submitList(List<Component> list) {
        components = list;
        this.componentsFull = new ArrayList<>(list);
        notifyDataSetChanged();
    }

    public void filter(String text) {
        List<Component> filteredList = new ArrayList<>();

        if (text == null || text.isEmpty()) {
            filteredList.addAll(componentsFull);
        } else {
            String filterPattern = text.toLowerCase().trim();
            for (Component item : componentsFull) {

                if (item.getName().toLowerCase().contains(filterPattern) ||
                        item.toString().toLowerCase().contains(filterPattern)) {
                    filteredList.add(item);
                }
            }
        }

        this.components = filteredList;
        notifyDataSetChanged();
    }

    static class VH extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name, description, price, idView;
        Button buildAddButton, cartAddButton;

        VH(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.product_image);
            name = itemView.findViewById(R.id.product_name);
            description = itemView.findViewById(R.id.product_description);
            price = itemView.findViewById(R.id.priceView);
            idView = itemView.findViewById(R.id.product_id);
            buildAddButton = itemView.findViewById(R.id.btnSavedRemoved);
            cartAddButton = itemView.findViewById(R.id.btnSavedAddtoCart);
        }
    }

}
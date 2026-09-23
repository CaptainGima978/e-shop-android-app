package gr.softeng.team16.view.SavedBuilds;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import gr.softeng.team16.R;
import gr.softeng.team16.domain.Build;

/**
 * Adapter for displaying saved builds in a RecyclerView.
 */
public class SavedBuildsAdapter extends RecyclerView.Adapter<SavedBuildsAdapter.BuildViewHolder>{

    /**
     * Listener interface for handling build deletion events.
     */
    public interface OnBuildDeleteListener{
        void onBuildDelete(Build build);
        void onBuildAddToCart(Build build);
    }

    private List<Build> builds = new ArrayList<>();
    private final OnBuildDeleteListener deleteListener;

    /**
     * Constructor initializing the adapter with a delete listener.
     * @param deleteListener Listener for handling build deletion events.
     */
    public SavedBuildsAdapter(OnBuildDeleteListener deleteListener) {
        this.deleteListener = deleteListener;
    }

    /**
     * Sets the list of builds to be displayed and notifies the adapter of data changes.
     * @param builds List of builds to display.
     */
    public void setBuilds(List<Build> builds){
        this.builds = builds;
        notifyDataSetChanged();
    }

    /**
     * Removes a build from the list and notifies the adapter of item removal.
     * @param build Build to be removed.
     */
    public void removeBuild(Build build) {
        int position = builds.indexOf(build);
        if (position != -1) {
            builds.remove(position);
            notifyItemRemoved(position);
        }
    }

    @NonNull
    @Override
    public BuildViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.saved_build_item, parent, false);
        return new BuildViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull BuildViewHolder holder, int position) {
        Build build = builds.get(position);

        // Set build name - Uses build.getName() instead of tower name
        if (holder.name != null) {
            holder.name.setText(build.getName());
        }
        
        // Load build image - Using Tower image as visual representation
        if (holder.productImage != null) {
            if (build.getTower() != null && build.getTower().getImg() != null) {
                Glide.with(holder.itemView.getContext())
                        .load(build.getTower().getImg())
                        .placeholder(R.drawable.tower_icon)
                        .into(holder.productImage);
            } else {
                holder.productImage.setImageResource(R.drawable.tower_icon);
            }
        }

        // Set build price
        if (holder.price != null) {
            holder.price.setText(String.format(Locale.US, "%.2f€", build.getPrice()));
        }

        // Set build description
        if (holder.description != null) {
            holder.description.setText(build.toString());
        }

        // Set build ID
        if (holder.productId != null) {
            holder.productId.setText("Build ID: " + build.getId());
        }


        if(holder.deleteButton!=null) {
            holder.deleteButton.setOnClickListener(v -> {
                if (deleteListener != null) {
                    deleteListener.onBuildDelete(build);
                }

            });
        }
        if(holder.cartButton!=null) {
            holder.cartButton.setOnClickListener(v -> {
                if (deleteListener != null) {
                    deleteListener.onBuildAddToCart(build);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return builds.size();
    }

    /**
     * ViewHolder class for holding build item views.
     */
    public static class BuildViewHolder extends RecyclerView.ViewHolder {
        public TextView name;

        public TextView price;
        public TextView description;
        public ImageView productImage;
        public TextView productId;
        public Button cartButton;
        public Button deleteButton;

        public BuildViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.product_name);
            price = itemView.findViewById(R.id.priceView);
            productImage = itemView.findViewById(R.id.product_image);
            description = itemView.findViewById(R.id.product_description);
            productId = itemView.findViewById(R.id.product_id);
            cartButton= itemView.findViewById(R.id.btnSavedAddtoCart);
            deleteButton = itemView.findViewById(R.id.btnSavedRemoved);
        }
    }
}

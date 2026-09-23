package gr.softeng.team16.view.offeredBuilds;

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
import gr.softeng.team16.domain.Admin_Build;
import gr.softeng.team16.domain.Build;

public class OfferedBuildsAdapter extends RecyclerView.Adapter<OfferedBuildsAdapter.BuildViewHolder> {

    private List<Admin_Build> builds;
    private final BuildItemListener listener;
    private final boolean isAdmin;

    public interface BuildItemListener {
        void onOpenInBuilder(Build build);
        void onAddOrRemove(Build build);
    }

    public OfferedBuildsAdapter( BuildItemListener listener, boolean isAdmin) {
        this.listener = listener;
        this.builds = new ArrayList<>();
        this.isAdmin = isAdmin;
    }

    public void setBuilds(List<Admin_Build> builds) {
        this.builds = builds;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BuildViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.offered_build_item, parent, false);
        return new BuildViewHolder(view, isAdmin);
    }

    @Override
    public void onBindViewHolder(@NonNull BuildViewHolder holder, int position) {
        Admin_Build build = builds.get(position);
        holder.bind(build, listener);
    }

    @Override
    public int getItemCount() {
        return builds.size();
    }

    static class BuildViewHolder extends RecyclerView.ViewHolder {
        private final ImageView image;
        private final TextView name;
        private final TextView price;
        private final TextView description;
        private final TextView id;
        private final Button btnOpenInBuilder;
        private final Button btnAddOrRemove;

        private final boolean isAdmin;

        public BuildViewHolder(@NonNull View itemView, boolean isAdmin) {
            super(itemView);
            image = itemView.findViewById(R.id.product_image);
            name = itemView.findViewById(R.id.product_name);
            price = itemView.findViewById(R.id.priceView);
            description = itemView.findViewById(R.id.product_description);
            id = itemView.findViewById(R.id.product_id);
            btnOpenInBuilder = itemView.findViewById(R.id.btnOpenInBuilder);
            btnAddOrRemove = itemView.findViewById(R.id.btnAddOrRemove);

            this.isAdmin = isAdmin;
        }

        public void bind(Admin_Build build, BuildItemListener listener) {
            name.setText(build.getName());
            price.setText(String.format(Locale.US, "%.2f€", build.getFinalPrice()));
            description.setText(build.toString());
            id.setText(String.valueOf(build.getId()));

            String url = build.getTower().getImg();
            Glide.with(image.getContext()).load(url)
                            .placeholder(R.drawable.ic_launcher_foreground)
                            .error(R.drawable.ic_launcher_foreground)
                            .into(image);

            btnOpenInBuilder.setOnClickListener(v -> listener.onOpenInBuilder(build));
            btnAddOrRemove.setOnClickListener(v -> listener.onAddOrRemove(build));
            if (isAdmin) {
                btnAddOrRemove.setText("Remove");
            }
        }
    }
}

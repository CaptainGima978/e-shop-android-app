package gr.softeng.team16.view.offeredBuilds;

import com.google.firebase.database.DatabaseError;

import java.util.List;

import gr.softeng.team16.data.DataRepository;
import gr.softeng.team16.data.LoadCallback;
import gr.softeng.team16.domain.Admin_Build;
import gr.softeng.team16.domain.Build;
import gr.softeng.team16.domain.OrderLine;
import gr.softeng.team16.view.build.BuildPresenter;
import gr.softeng.team16.view.cart.cartView.CartPresenter;

public class OfferedBuildsPresenter {

    private static OfferedBuildsPresenter instance;
    private OfferedBuildsView view;
    private DataRepository repo;

    private OfferedBuildsPresenter() {}

    public static OfferedBuildsPresenter getInstance() {
        if (instance == null) {
            instance = new OfferedBuildsPresenter();
        }
        return instance;
    }

    public void setView(OfferedBuildsView view) {
        this.view = view;
    }

    public void setRepo(DataRepository repo) {
        this.repo = repo;
    }

    public void openInBuilder(Build build) {
        BuildPresenter.getInstance().setBuild(build);
        view.showSuccess(build.getName() + " opened in builder");
    }

    public void removeBuild(Build build) {
        repo.deleteOfferedBuild(build);
        view.showSuccess(build.getName() + " removed from Offered Builds");
        loadBuilds();
    }

    public void addToCart(Build build) {
        build.setPrice(build.getFinalPrice());
        CartPresenter.getInstance().addToCart(new OrderLine(build));
        view.showSuccess(build.getName() + " added to cart");
    }

    public void loadBuilds() {
        repo.getOfferedBuilds(new LoadCallback<List<Admin_Build>>() {
            @Override
            public void onLoaded(List<Admin_Build> result) {
                view.loadBuilds(result);
            }

            @Override
            public void onError(DatabaseError error) {
                view.showSuccess("Error loading builds");
            }
        });
    }
}

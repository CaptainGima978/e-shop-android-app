package gr.softeng.team16.view.offeredBuilds;

import java.util.List;

import gr.softeng.team16.domain.Admin_Build;
import gr.softeng.team16.domain.Build;

public class OfferedBuildsViewStub implements OfferedBuildsView {
    private String lastMessage;
    private List<Admin_Build> loadedBuilds;

    @Override
    public void showSuccess(String message) {
        this.lastMessage = message;
    }

    @Override
    public void loadBuilds(List<Admin_Build> builds) {
        this.loadedBuilds = builds;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public List<Admin_Build> getLoadedBuilds() {
        return loadedBuilds;
    }
}

package gr.softeng.team16.view.offeredBuilds;

import java.util.List;

import gr.softeng.team16.domain.Admin_Build;
import gr.softeng.team16.domain.Build;

public interface OfferedBuildsView {
    void showSuccess(String message);
    void loadBuilds(List<Admin_Build> builds);
}

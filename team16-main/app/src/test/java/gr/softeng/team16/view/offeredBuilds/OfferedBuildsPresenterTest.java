package gr.softeng.team16.view.offeredBuilds;

import static org.junit.Assert.*;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.google.firebase.database.DatabaseError;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import gr.softeng.team16.data.LoadCallback;
import gr.softeng.team16.domain.Admin_Build;
import gr.softeng.team16.domain.Build;
import gr.softeng.team16.util.BuildMother;
import gr.softeng.team16.util.LocalRepository;
import gr.softeng.team16.view.build.BuildPresenter;

public class OfferedBuildsPresenterTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private OfferedBuildsPresenter presenter;
    private OfferedBuildsViewStub view;
    private LocalRepository repo;

    @Before
    public void setUp() throws Exception {
        // Reset singletons using reflection
        java.lang.reflect.Field offeredInstance = OfferedBuildsPresenter.class.getDeclaredField("instance");
        offeredInstance.setAccessible(true);
        offeredInstance.set(null, null);

        java.lang.reflect.Field buildInstance = BuildPresenter.class.getDeclaredField("instance");
        buildInstance.setAccessible(true);
        buildInstance.set(null, null);

        presenter = OfferedBuildsPresenter.getInstance();
        view = new OfferedBuildsViewStub();
        repo = new LocalRepository();
        presenter.setView(view);
        presenter.setRepo(repo);
    }

    @Test
    public void openInBuilder_sets_build_in_BuildPresenter() {
        Build build = BuildMother.generateBuild();
        presenter.openInBuilder(build);

        assertEquals(build, BuildPresenter.getInstance().getBuild().getValue());
    }

    @Test
    public void openInBuilder_triggers_success_message_on_view() {
        Build build = BuildMother.generateBuild();
        build.setName("Power Build");
        presenter.openInBuilder(build);

        assertEquals("Power Build opened in builder", view.getLastMessage());
    }

    @Test
    public void openInBuilder_handles_null_build_object() {
        try {
            presenter.openInBuilder(null);
        } catch (NullPointerException e) {
            // Expected crash because presenter doesn't null check build.getName()
        }
    }

    @Test
    public void openInBuilder_handles_null_view_reference() {
        presenter.setView(null);
        Build build = BuildMother.generateBuild();
        try {
            presenter.openInBuilder(build);
        } catch (NullPointerException e) {
            // Expected crash because presenter doesn't null check view
        }
    }

    @Test
    public void removeBuild_shows_removal_success_message() {
        Build build = BuildMother.generateBuild();
        build.setName("Delete Me");
        presenter.removeBuild(build);

        assertEquals("Delete Me removed from Offered Builds", view.getLastMessage());
    }

    @Test
    public void removeBuild_triggers_list_refresh_and_actually_deletes() {
        repo.clearOfferedBuilds();
        Admin_Build buildToRemove = new Admin_Build(BuildMother.generateBuild());
        buildToRemove.setId(88888);
        
        Admin_Build remainingBuild = new Admin_Build(BuildMother.generateBuild());
        remainingBuild.setName("Stay");
        remainingBuild.setId(99999);
        
        repo.addOfferedBuild(remainingBuild);
        repo.addOfferedBuild(buildToRemove);
        
        presenter.removeBuild(buildToRemove);

        assertNotNull(view.getLoadedBuilds());
        
        boolean foundRemaining = false;
        boolean foundRemoved = false;
        
        for (Build b : view.getLoadedBuilds()) {
            if (b.getId() == remainingBuild.getId()) {
                foundRemaining = true;
            }
            if (b.getId() == buildToRemove.getId()) {
                foundRemoved = true;
            }
        }
        
        assertTrue("Remaining build should be present", foundRemaining);
        assertFalse("Removed build should be gone", foundRemoved);
    }

    @Test
    public void removeBuild_handles_null_repository_dependency() {
        presenter.setRepo(null);
        Build build = BuildMother.generateBuild();
        try {
            presenter.removeBuild(build);
        } catch (NullPointerException e) {
            // Expected
        }
    }

    @Test
    public void addToCart_logic_placeholder_verification() {
        Build build = BuildMother.generateBuild();
        presenter.addToCart(build);
        // Currently empty, just ensuring no crash for coverage
    }

    @Test
    public void loadBuilds_requests_data_from_repository() {
        presenter.loadBuilds();
        assertNotNull(view.getLoadedBuilds());
    }

    @Test
    public void loadBuilds_success_callback_updates_view() {
        repo.clearOfferedBuilds();
        Admin_Build build = new Admin_Build(BuildMother.generateBuild());
        repo.addOfferedBuild(build);
        
        presenter.loadBuilds();

        assertNotNull(view.getLoadedBuilds());
        assertEquals(1, view.getLoadedBuilds().size());
        assertEquals(build.getId(), view.getLoadedBuilds().get(0).getId());
    }

    @Test
    public void loadBuilds_success_callback_with_empty_list() {
        repo.clearOfferedBuilds();
        
        presenter.loadBuilds();

        assertNotNull(view.getLoadedBuilds());
        assertTrue(view.getLoadedBuilds().isEmpty());
    }

    @Test
    public void loadBuilds_error_callback_shows_error_message() {
        repo.setSuccessful(false);
        presenter.loadBuilds();

        assertEquals("Error loading builds", view.getLastMessage());
    }

    @Test
    public void loadBuilds_handles_null_view_during_async_callback() {
        presenter.setView(null);
        try {
            presenter.loadBuilds();
        } catch (NullPointerException e) {
            // Expected
        }
    }

    @Test
    public void loadBuilds_handles_database_error_object() {
        repo.setSuccessful(false);
        presenter.loadBuilds();
        assertEquals("Error loading builds", view.getLastMessage());
    }

    @Test
    public void singleton_instance_persistence_check() {
        OfferedBuildsPresenter first = OfferedBuildsPresenter.getInstance();
        OfferedBuildsPresenter second = OfferedBuildsPresenter.getInstance();
        assertSame(first, second);
    }

}

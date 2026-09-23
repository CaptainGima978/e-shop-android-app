package gr.softeng.team16.view.SavedBuilds;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import gr.softeng.team16.domain.Build;
import gr.softeng.team16.util.LocalRepository;

public class SavedBuildsPresenterTest {
    private SavedBuildsPresenter presenter;
    private SavedBuildsViewStub viewStub;

    private LocalRepository repo;


    @Before
    public void setUp() throws Exception {
        viewStub = new SavedBuildsViewStub();
        repo = new LocalRepository();
        presenter = new SavedBuildsPresenter(repo);
        presenter.setView(viewStub);

    }

    @Test
    public void testLoadBuildsSuccess() {
        List<Build> builds = new ArrayList<>();
        builds.add(new Build());
        repo.setSuccessful(true);
        repo.setBuildsToReturn(builds);

        presenter.loadBuilds();

        assertTrue(viewStub.displaySavedBuildsCalled);
        assertFalse(viewStub.showEmptyMessageCalled);
    }
    @Test
    public void testBuildsEmpty() {
        repo.setSuccessful(true);
        repo.setBuildsToReturn(new ArrayList<Build>());

        presenter.loadBuilds();

        assertTrue(viewStub.showEmptyMessageCalled);

    }

    @Test
    public void testLoadBuildsError() {
        repo.setSuccessful(false);

        presenter.loadBuilds();

        assertTrue(viewStub.errorCount > 0);
        assertNotNull(viewStub.errorMessage);
    }

    @Test
    public void testDeleteBuildSuccess() {
        Build testBuild = new Build();
        testBuild.setFirebaseKey("testKey");
        repo.setDeleteShouldSucceed(true);

        presenter.deleteBuild(testBuild);

        assertTrue(viewStub.onBuildDeletedCalled);
        assertEquals("testKey", viewStub.deleteBuild.getFirebaseKey());
    }

    @Test
    public void testDeleteBuildFailure() {
        repo.setDeleteShouldSucceed(false);


        Build testBuild = new Build();
        testBuild.setFirebaseKey("any_key");

        presenter.deleteBuild(testBuild);


        assertTrue(viewStub.errorCount > 0);

    }



}


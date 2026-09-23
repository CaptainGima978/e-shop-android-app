package gr.softeng.team16.view.registration;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import gr.softeng.team16.data.FirebaseRepository;
import gr.softeng.team16.util.LocalRepository;

public class RegistrationPresenterTest {
    private RegistrationPresenter presenter;
    private RegistrationViewStub view;

    private LocalRepository repo;

    @Before
    public void setUp() throws Exception {
        view = new RegistrationViewStub();
        repo = new LocalRepository();
        presenter = new RegistrationPresenter(repo);
        presenter.setView(view);
    }


    @Test
    public void ValidationOfUsernameAlreadyExists() {

        repo.setUsernameAvailable(false);

        presenter.checkUsernameLive("user");

        assertFalse("Username should be marked as invalid",view.isUsernameValid());
        assertTrue("Error count shoud be increased",view.getErrorCount()>0);

    }
    @Test
    public void testCheckRegisterDatabaseError() {

        repo.setSuccessful(false);

        presenter.CheckRegister("Nikos", "Papad", "test@mail.com", "user123", "pass123");

        assertEquals("Database Error: Database Error", view.getErrormessage());
        assertTrue(view.getErrorCount() > 0);
    }

    @Test
    public void testUsernameTaken() {
        repo.setSuccessful(true);
        repo.setUsernameAvailable(false);

        presenter.checkUsernameLive("existing_user");

        assertFalse(view.isUsernameValid());
        assertTrue(view.getErrorCount() > 0);
    }
    @Test
    public void testEmailAvailabilityTrue() {
        repo.setSuccessful(true);

        presenter.checkEmailLive("new@email.com");

        assertEquals(0, view.getErrorCount());
    }

    @Test
    public void testEmailAvailabilityFalse() {

        repo.setSuccessful(true);
        repo.setEmailAvailable(false);

        presenter.checkEmailLive("taken@email.com");


    }

    @Test
    public void CheckDuplicateEmail() {
        repo.setUsernameAvailable(true);
        repo.setSuccessful(true);


        repo.setAuthSuccessful(false);

        presenter.CheckRegister("Nikos", "Papadopoulos", "duplicate@test.com", "user123", "password123");


        assertFalse(view.isSuccess());
        assertTrue(view.getErrorCount() > 0);

    }
    @Test
    public void testSuccessOfRegistration(){
        repo.setSuccessful(true);
        repo.setUsernameAvailable(true);


        presenter.CheckRegister("Nikos", "Papadopoulos", "test@mail.com", "nikos123", "pass123456");

        assertTrue(view.isSuccess());
        assertEquals(0, view.getErrorCount());
    }

    @Test
    public void testUsernameCheckDatabaseError() {

        repo.setSuccessful(false);

        presenter.checkUsernameLive("user123");

        assertNotNull(view.getErrormessage());

    }

    @Test
    public void testEmailCheckDatabaseError() {

        repo.setSuccessful(false);

        presenter.checkEmailLive("test@mail.com");


        assertNotNull(view.getErrormessage());
    }

    @Test
    public void testRegistrationFailure() {
        repo.setUsernameAvailable(true);

        repo.setSuccessful(false);

        presenter.CheckRegister("First", "Last", "email@test.com", "user", "pass");


        assertFalse(view.isSuccess());
        assertTrue(view.getErrorCount() > 0);
    }

    @Test
    public void testCancelRegistration(){
        presenter.onCancelClick();

        assertTrue(view.isNavigatedTomain());

    }
    @Test
    public void testInitialState(){
        assertEquals(0,view.getErrorCount());
        assertTrue(view.isUsernameValid());
    }

}
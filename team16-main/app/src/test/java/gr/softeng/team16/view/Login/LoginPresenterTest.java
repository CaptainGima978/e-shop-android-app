package gr.softeng.team16.view.Login;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import gr.softeng.team16.data.FirebaseRepository;
import gr.softeng.team16.util.LocalRepository;

public class LoginPresenterTest {
    private LoginPresenter presenter;
    private LoginViewStub view;

    private LocalRepository repo;

    @Before
    public void setUp() throws Exception {
        view = new LoginViewStub();
        repo = new LocalRepository();
        repo.setSuccessful(true);
        presenter = new LoginPresenter(repo);
        presenter.setView(view);
    }
    @Test
    public void ValidationOfLoginSuccess(){
        repo.setSuccessful(true);
        repo.setEmailtoReturn("user@test.com");


        presenter.onLoginClick("validUser", "pass123");


        assertTrue(view.isLoginConfirmed());
        assertTrue(view.isUsernameValid());
        assertTrue(view.isPasswordCorrect());
        assertEquals(0, view.getErrorCount());
    }

    @Test
    public void testCancelLogin(){
        presenter.onCancelClick();
        assertTrue(view.isNavigatedTomain());
    }

    @Test
    public void ValidationOfUsernameNotFound(){
        repo.setSuccessful(false);

        presenter.onLoginClick("wrongUser", "anyPass");

        assertFalse(view.isUsernameValid());
        assertTrue(view.getErrorCount() > 0);
        assertFalse(view.isLoginConfirmed());

    }

    @Test
    public void ValidationOfWrongPassword(){
        repo.setSuccessful(true);
        repo.setEmailtoReturn("user@test.com");

        repo.setAuthSuccessful(false);

        presenter.onLoginClick("user", "wrongPass");

        assertFalse(view.isPasswordCorrect());
        assertTrue(view.getErrorCount() > 0);
    }

    @Test
    public void testEmptyUsername() {

        repo.setSuccessful(true);


        presenter.onLoginClick("", "password123");


        assertFalse("Username should be invalid when empty", view.isUsernameValid());
        assertTrue("Error count should increase", view.getErrorCount() > 0);
        assertFalse("Login should not be confirmed", view.isLoginConfirmed());
    }

    @Test
    public void testEmptyPassword() {

        repo.setSuccessful(true);


        presenter.onLoginClick("validUser", "");


        assertFalse("Password should be invalid when empty", view.isPasswordCorrect());
        assertTrue("Error count should increase", view.getErrorCount() > 0);
        assertFalse("Login should not be confirmed", view.isLoginConfirmed());
    }

    @Test
    public void testBothFieldsEmpty() {

        presenter.onLoginClick("", "");


        assertFalse(view.isUsernameValid());
        assertFalse(view.isLoginConfirmed());
    }
    @Test
    public void testOnUsernameChangedSuccess() {

        repo.setSuccessful(true);
        repo.setEmailtoReturn("test@example.com");


        presenter.onUsernameChanged("existingUser");

        assertTrue(view.isUsernameValid());
    }

    @Test
    public void testOnUsernameChangedFailure() {

        repo.setSuccessful(false);

        presenter.onUsernameChanged("nonExistentUser");


        assertFalse(view.isUsernameValid());
    }


    @Test
    public void testAdminLogin(){
        repo.setSuccessful(true);
        repo.setEmailtoReturn("admin@build.com");

        presenter.onLoginClick("admin", "adminPass");

        assertTrue(view.isAdminLoginConfirmed());


    }


}
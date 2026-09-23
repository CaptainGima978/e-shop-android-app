package gr.softeng.team16.view.cart.personalDetails;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import gr.softeng.team16.util.LocalRepository;
import gr.softeng.team16.view.Login.LoginPresenter;
import gr.softeng.team16.view.Login.LoginViewStub;

public class fillPersonalDetailsPresenterTest {

    private fillPersonalDetailsPresenter presenter;
    private fillPersonalDetailsViewStub view;

    private LocalRepository repo;

    @Before
    public void setUp() throws Exception {
        view = new fillPersonalDetailsViewStub();
        repo = new LocalRepository();
        repo.setSuccessful(true);
        presenter = new fillPersonalDetailsPresenter(repo);
        presenter.setView(view);
    }

    @Test
    public void testCancelfill_personalData(){
        presenter.onCancelClick();
        assertTrue(view.isNavigatedToHomePage());
    }

    @Test
    public void checkName_whenNull() {
        presenter.checkName(null);
        assertFalse(view.isNameValid());
    }

    @Test
    public void checkName_whenFalse() {
        presenter.checkName("John123");
        assertFalse(view.isNameValid());
    }

    @Test
    public void checkName_whenCorrect() {
        presenter.checkName("John");
        assertTrue(view.isNameValid());
    }

    @Test
    public void checkSurname_whenNull() {
        presenter.checkSurname(null);
        assertFalse(view.isSurnameValid());
    }

    @Test
    public void checkSurname_whenFalse() {
        presenter.checkSurname("Doe_1");
        assertFalse(view.isSurnameValid());
    }

    @Test
    public void checkSurname_whenCorrect() {
        presenter.checkSurname("Doe");
        assertTrue(view.isSurnameValid());
    }

    @Test
    public void checkAddress_whenNull() {
        presenter.checkAddress(null);
        assertFalse(view.isAddressValid());
    }

    @Test
    public void checkAddress_whenFalse() {
        presenter.checkAddress("Arkadiou 1234");
        assertFalse(view.isAddressValid());
    }

    @Test
    public void checkAddress_whenTrue() {
        presenter.checkAddress("Arkadiou 5");
        assertTrue(view.isAddressValid());
    }

    @Test
    public void checkCity_whenNull() {
        presenter.checkCity(null);
        assertFalse(view.isCityValid());
    }

    @Test
    public void checkCity_whenFalse() {
        presenter.checkCity("Peristeri_12");
        assertFalse(view.isCityValid());
    }

    @Test
    public void checkCity_whenTrue() {
        presenter.checkCity("Petroupoli");
        assertTrue(view.isCityValid());
    }

    @Test
    public void checkZipcode_whenNull() {
        presenter.checkZipCode(null);
        assertFalse(view.isZipCodeCorrect());
    }

    @Test
    public void checkZipcode_whenFalse() {
        presenter.checkZipCode("1323232");
        assertFalse(view.isZipCodeCorrect());
    }


    @Test
    public void checkZipcode_whenFalseWithLetters() {
        presenter.checkZipCode("1323A2B");
        assertFalse(view.isZipCodeCorrect());
    }

    @Test
    public void checkZipcode_whenTrue() {
        presenter.checkZipCode("13232");
        assertTrue(view.isZipCodeCorrect());
    }

    @Test
    public void checkEmail() {
    }

    @Test
    public void testOnContinueClick_navigatesToPayment() {
        presenter.onContinueClick("John", "Doe", "Arkadiou 5", "Athens", "12345", "test@gmail.com");
        assertTrue(view.isNavigatedToPayment());
    }
}
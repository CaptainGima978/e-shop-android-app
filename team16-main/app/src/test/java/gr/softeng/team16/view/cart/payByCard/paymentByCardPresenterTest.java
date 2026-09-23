package gr.softeng.team16.view.cart.payByCard;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import gr.softeng.team16.domain.Address;
import gr.softeng.team16.util.LocalRepository;
import gr.softeng.team16.view.cart.cartView.CartPresenter;
import gr.softeng.team16.view.cart.personalDetails.fillPersonalDetailsPresenter;
import gr.softeng.team16.view.cart.personalDetails.fillPersonalDetailsViewStub;

public class paymentByCardPresenterTest {
    private paymentByCardPresenter presenter;
    private paymentByCardViewStub viewStub;

    private LocalRepository repo;

    @Before
    public void setUp() throws Exception {
        viewStub = new paymentByCardViewStub();
        repo = new LocalRepository();
        repo.setSuccessful(true);
        presenter = new paymentByCardPresenter(repo);
        presenter.setView(viewStub);
        CartPresenter testCartPresenter = new CartPresenter(repo);
        CartPresenter.setInstance(testCartPresenter);
    }

    @Test
    public void checkCardNumber_whenNull() {
        presenter.checkCardNumber(null);
        assertFalse(viewStub.isCardNumberValid());
    }

    @Test
    public void checkCardNumber_whenFalse() {
        presenter.checkCardNumber("12345");
        assertFalse(viewStub.isCardNumberValid());
    }

    @Test
    public void checkName_whenCorrect() {
        presenter.checkCardNumber("1212121212121212");
        assertTrue(viewStub.isCardNumberValid());
    }

    @Test
    public void checkCardHolder_whenNull() {
        presenter.checkCardHolder(null);
        assertFalse(viewStub.isCardHolderValid());
    }

    @Test
    public void checkCardHolder_whenFalse() {
        presenter.checkCardHolder("Alex123");
        assertFalse(viewStub.isCardHolderValid());
    }

    @Test
    public void checkCardHolder_whenTrue() {
        presenter.checkCardHolder("John Smith");
        assertTrue(viewStub.isCardHolderValid());
    }

    @Test
    public void checkExpDate_whenNull() {
        presenter.checkExpDate(null);
        assertFalse(viewStub.isExpDateValid());
    }

    @Test
    public void checkExpDate_whenFalse() {
        presenter.checkExpDate("John");
        assertFalse(viewStub.isExpDateValid());
    }

    @Test
    public void checkExpDate_whenFalse_DatePassed() {
        presenter.checkExpDate("12/24");
        assertFalse(viewStub.isExpDateValid());
    }

    @Test
    public void checkExpDate_whenTrue() {
        presenter.checkExpDate("12/28");
        assertTrue(viewStub.isExpDateValid());
    }

    @Test
    public void checkCVC_whenNull() {
        presenter.checkCVC(null);
        assertFalse(viewStub.isCVCValid());
    }

    @Test
    public void checkCVC_whenFalse() {
        presenter.checkCVC("1234");
        assertFalse(viewStub.isCVCValid());
    }

    @Test
    public void checkCVC_whenTrue() {
        presenter.checkCVC("123");
        assertTrue(viewStub.isCVCValid());
    }


    @Test
    public void testOnCancelClick_navigatesToPreviousPage() {
        presenter.onCancelClick();
        assertTrue(viewStub.isNavigatedToPreviousPage());
    }

    @Test
    public void testOnPayClick() {
        Address address = new Address("Peristeri","Knwsou", 5, "13232");
        presenter.onPayClick("1212121212121234", "John Doe", "12/28", "123", address);

        Assert.assertEquals(1, repo.getOrderList().size()); //We make sure that the order was placed
        Assert.assertEquals(address, repo.getOrderList().get(0).getAddress()); // We make sure that the order was placed to the address we wanted
    }
}
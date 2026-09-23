package gr.softeng.team16.view.orders.userOrders;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import gr.softeng.team16.util.LocalRepository;

public class userOrderPresenterTest {

    private userOrderPresenter presenter;
    private userOrderViewStub view;
    private LocalRepository repo;

    @Before
    public void setUp() {
        presenter = new userOrderPresenter();
        view = new userOrderViewStub();
        repo = new LocalRepository();
        presenter.setView(view);
    }

// We had some very serious problems with the loadOrders(),removeOrder(), testOnBackClick() tests (we use Firebase calls on userOrderPresenter and when we tried to use repo calls from the DataRepository, they didn't work and the app had bugs), so we weren't able to do the tests on them


    @Test
    public void loadOrders() {
    }

    @Test
    public void removeOrder() {
    }

    @Test
    public void testOnBackClick(){

    }
}

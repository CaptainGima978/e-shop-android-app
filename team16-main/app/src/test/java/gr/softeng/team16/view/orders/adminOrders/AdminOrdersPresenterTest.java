package gr.softeng.team16.view.orders.adminOrders;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import gr.softeng.team16.domain.Order;
import gr.softeng.team16.util.LocalRepository;

public class AdminOrdersPresenterTest {

    private AdminOrdersPresenter presenter;
    private AdminOrdersViewStub view;
    private LocalRepository repo;

    @Before
    public void setUp() {
        presenter = new AdminOrdersPresenter();
        view = new AdminOrdersViewStub();
        repo = new LocalRepository();
    }

    @Test
    public void loadOrders_with_null_repository() {
        presenter.setView(view);
        presenter.setRepository(null);
        presenter.loadOrders();
        
        Assert.assertEquals(0, view.getShowOrdersCalls());
    }

    @Test
    public void loadOrders_with_null_view() {
        presenter.setView(null);
        presenter.setRepository(repo);
        presenter.loadOrders();
        
        // No way to check repo interaction easily without mockito, but we can check it doesn't crash.
        // The implementation has a guard: if (view == null) return;
    }

    @Test
    public void loadOrders_successful_callback() {
        repo.addOrder(new Order(1, 10.0));
        presenter.setView(view);
        presenter.setRepository(repo);
        
        presenter.loadOrders();
        
        Assert.assertEquals(1, view.getShowOrdersCalls());
        Assert.assertEquals(1, view.getOrders().size());
    }

    @Test
    public void loadOrders_database_error_callback() {
        repo.setSuccessful(false);
        presenter.setView(view);
        presenter.setRepository(repo);
        
        presenter.loadOrders();
        
        Assert.assertEquals(1, view.getShowErrorCalls());
    }

    @Test
    public void loadOrders_with_empty_order_list() {
        presenter.setView(view);
        presenter.setRepository(repo);
        
        presenter.loadOrders();
        
        Assert.assertEquals(1, view.getShowOrdersCalls());
        Assert.assertEquals(0, view.getOrders().size());
    }

    @Test
    public void showOrders_successful_callback_execution() {
        presenter.setView(view);
        presenter.setRepository(repo);
        
        presenter.showOrders();
        
        Assert.assertEquals(1, view.getShowOrdersCalls());
    }

    @Test(expected = NullPointerException.class)
    public void showOrders_null_pointer_vulnerability_check() {
        presenter.setRepository(null);
        presenter.showOrders();
    }

    @Test
    public void showOrders_error_callback_silent_failure() {
        repo.setSuccessful(false);
        presenter.setView(view);
        presenter.setRepository(repo);
        
        presenter.showOrders();
        
        Assert.assertEquals(0, view.getShowOrdersCalls());
        Assert.assertEquals(0, view.getShowErrorCalls());
    }

    @Test
    public void acceptOrder_repository_interaction() {
        Order order = new Order(1, 10.0);
        repo.addOrder(order);
        presenter.setView(view);
        presenter.setRepository(repo);
        
        presenter.acceptOrder(order);
        
        Assert.assertEquals("ACCEPTED", repo.getOrderList().get(0).getStatus());
    }

    @Test
    public void acceptOrder_triggers_data_refresh() {
        Order order = new Order(1, 10.0);
        repo.addOrder(order);
        presenter.setView(view);
        presenter.setRepository(repo);
        
        presenter.acceptOrder(order);
        
        // acceptOrder calls loadOrders(), which calls showOrders()
        Assert.assertEquals(1, view.getShowOrdersCalls());
    }

    @Test
    public void acceptOrder_with_null_order_object() {
        presenter.setView(view);
        presenter.setRepository(repo);
        
        // LocalRepository.acceptOrder handles null, and presenter calls it directly.
        presenter.acceptOrder(null);
        // Should not crash.
    }

    @Test
    public void acceptOrder_loadOrders_dependency_check() {
        presenter.setView(null);
        presenter.setRepository(repo);
        
        presenter.acceptOrder(new Order(1, 10.0));
        // Should not crash due to loadOrders defensive checks (if (view == null) return;).
    }

}

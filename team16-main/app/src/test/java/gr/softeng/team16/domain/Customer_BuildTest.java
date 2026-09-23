package gr.softeng.team16.domain;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import gr.softeng.team16.util.BuildMother;

public class Customer_BuildTest {

    private Customer_Build customerBuild;

    @Before
    public void setUp() throws Exception {
        customerBuild = new Customer_Build("Test Build");
        BuildMother.fillBuild(customerBuild);
    }

    @Test
    public void publishBuild() {
        customerBuild.publishBuild();
        assertFalse(customerBuild.isPublished());

        customerBuild.setCustomerAccount(new CustomerAccount("John Doe", "johndoe", "johndoe@xample.com", new Address("Athens", "Main St", 123, "12345")));
        customerBuild.publishBuild();
        assertTrue(customerBuild.isPublished());

        customerBuild.unpublishBuild();
        assertFalse(customerBuild.isPublished());
    }

    @Test
    public void like() {
        customerBuild = new Customer_Build("Test Build", new CustomerAccount("John Doe", "johndoe", "johndoe@xample.com", new Address("Athens", "Main St", 123, "12345")));
        assertNotNull(customerBuild.getCustomerAccount());

        BuildMother.fillBuild(customerBuild);
        customerBuild.publishBuild();
        customerBuild.like();
        customerBuild.like();
        assertEquals(2, customerBuild.getLikes());
    }
}
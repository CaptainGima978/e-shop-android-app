package gr.softeng.team16.domain;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import gr.softeng.team16.util.BuildMother;

public class AdminBuildTest {

    private Admin_Build admin_build;

    @Before
    public void setUp() throws Exception {
        admin_build = new Admin_Build("Admin Build");
        admin_build.setName("Admin Build");
        BuildMother.fillBuild(admin_build);

        admin_build.updateOriginalPrice();
    }

    @Test
    public void getDiscountPrice() {
        admin_build.setDiscountPrice(1100);
        assertEquals(1100, admin_build.getDiscountPrice(), 0.01);

    }

    @Test
    public void getFinalPrice() {
        admin_build.setDiscountPrice(1100);
        assertEquals(1230.86, admin_build.getFinalPrice(), 0.01);

        admin_build.setOnDiscount();
        assertEquals(1100, admin_build.getFinalPrice(), 0.01);

        admin_build.setDiscountPrice(1000);
        assertEquals(1000, admin_build.getFinalPrice(), 0.01);

        admin_build.setOffDiscount();
        assertEquals(1230.86, admin_build.getFinalPrice(), 0.01);


    }
}

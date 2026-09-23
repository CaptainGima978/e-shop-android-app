package gr.softeng.team16.view.componentList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import gr.softeng.team16.domain.CPU;
import gr.softeng.team16.domain.Component;
import gr.softeng.team16.util.BuildMother;
import gr.softeng.team16.view.build.BuildPresenter;
import gr.softeng.team16.view.componentEditor.ComponentEditorPresenter;

public class ComponentListPresenterTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private ComponentListPresenter presenter;
    private ComponentListViewStub view;

    @Before
    public void setUp() {
        BuildPresenter.getInstance().createNewBuild();
        presenter = new ComponentListPresenter();
        view = new ComponentListViewStub();
        presenter.setView(view);
    }

    @Test
    public void loadComponents_valid_category_interaction() {
        presenter.loadComponents("CPU");

        assertEquals("CPU", view.getCategory());
        assertEquals(CPU.class, view.getCategoryClass());
        assertTrue(view.isShowComponentsCalled());
    }

    @Test
    public void loadComponents_null_view_safety() {
        presenter.setView(null);
        presenter.loadComponents("CPU");
        // No exception should be thrown
    }

    @Test
    public void loadComponents_invalid_category_mapping() {
        presenter.loadComponents("InvalidCategory");

        assertEquals("InvalidCategory", view.getCategory());
        assertNull(view.getCategoryClass());
    }

    @Test
    public void loadComponents_null_category_input() {
        presenter.loadComponents(null);

        assertNull(view.getCategory());
        assertNull(view.getCategoryClass());
    }

    @Test
    public void addComponentToBuild_success_state() {
        Component cpu = BuildMother.generateCPU();
        presenter.addComponentToBuild(cpu);

        assertNull(view.getErrorMessage());
    }

    @Test
    public void addComponentToBuild_failure_state_notification() {
        Component cpu1 = BuildMother.generateCPU();
        Component cpu2 = BuildMother.generateCPU();
        
        presenter.addComponentToBuild(cpu1);
        presenter.addComponentToBuild(cpu2);

        assertNotNull(view.getErrorMessage());
        assertTrue(view.getErrorMessage().contains(cpu2.getName()));
    }

    @Test
    public void addComponentToBuild_null_view_verification() {
        presenter.setView(null);
        presenter.addComponentToBuild(BuildMother.generateCPU());
        // Should not crash
    }

    @Test(expected = NullPointerException.class)
    public void addComponentToBuild_null_component_edge_case() {
        presenter.addComponentToBuild(null);
    }

    @Test
    public void addComponentToCart_successful_addition() {
        Component cpu = BuildMother.generateCPU();
        presenter.addComponentToCart(cpu);

        assertNotNull(view.getSuccessMessage());
        assertTrue(view.getSuccessMessage().contains(cpu.getName()));
    }

    @Test
    public void addComponentToCart_null_view_handling() {
        presenter.setView(null);
        presenter.addComponentToCart(BuildMother.generateCPU());
        // Should not crash
    }

    @Test
    public void completeComponentSelection_view_notification() {
        presenter.completeComponentSelection();
        assertTrue(view.isClosedWithSuccess());
    }

    @Test
    public void completeComponentSelection_null_view_protection() {
        presenter.setView(null);
        presenter.completeComponentSelection();
        // Should not crash
    }

    @Test
    public void setView_reference_update() {
        ComponentListViewStub newView = new ComponentListViewStub();
        presenter.setView(newView);
        
        presenter.completeComponentSelection();
        assertTrue(newView.isClosedWithSuccess());
        assertFalse(view.isClosedWithSuccess());
    }
    
    @Test
    public void sendComponentToEditor_presenter_interaction() {
        Component cpu = BuildMother.generateCPU();
        presenter.sendToEditor(cpu);

        assertEquals(cpu, ComponentEditorPresenter.getInstance().getComponent());
    }
}

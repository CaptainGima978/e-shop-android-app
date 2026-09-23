package gr.softeng.team16.view.componentList;

import java.util.HashMap;

import gr.softeng.team16.domain.*;
import gr.softeng.team16.view.build.BuildPresenter;
import gr.softeng.team16.view.cart.cartView.CartPresenter;
import gr.softeng.team16.view.componentEditor.ComponentEditorPresenter;

public class ComponentListPresenter {

    private ComponentListView view;
    private final BuildPresenter buildPresenter;
    
    private static final HashMap<String, Class<? extends Component>> categoryClass = new HashMap<> ();
    static {
        categoryClass.put("Tower", Tower.class);
        categoryClass.put("Motherboard", Motherboard.class);
        categoryClass.put("CPU", CPU.class);
        categoryClass.put("GPU", GPU.class);
        categoryClass.put("Memory", Memory.class);
        categoryClass.put("Cooler", Cooler.class);
        categoryClass.put("Storage", Storage.class);
        categoryClass.put("PSU", PSU.class);
    }

    public ComponentListPresenter() {
        this.buildPresenter = BuildPresenter.getInstance();
    }

    public void setView(ComponentListView view) {
        this.view = view;
    }

    public void loadComponents(String category) {
        if (view != null) {
            view.setCategory(category);
            view.setCategoryClass(categoryClass.get(category));
            view.showComponents();
        }
    }

    public void addComponentToBuild(Component component) {
        boolean componentAdded = buildPresenter.addComponentToBuild(component);
        if (view != null && !componentAdded) {
            view.showError(component.getName() + " cannot be added to your Build!");
        }
    }

    public void addComponentToCart(Component component) {
        boolean added = CartPresenter.getInstance().addToCart(new OrderLine(component));
        if (view != null) {
            if (added) {
                view.showSuccess(component.getName() + " added to Cart!");
            } else {
                // code never reach that point but anyway...
                view.showError("Failed to add " + component.getName() + " to Cart.");
            }
        }

    }

    public void completeComponentSelection() {
        if (view != null) {
            view.closeWithSuccess();
        }
    }

    public void sendToEditor(Component component) {
        ComponentEditorPresenter editPresenter = ComponentEditorPresenter.getInstance();
        editPresenter.setComponent(component);
    }

}

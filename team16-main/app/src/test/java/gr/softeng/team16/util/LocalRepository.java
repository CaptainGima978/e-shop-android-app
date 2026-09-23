package gr.softeng.team16.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import gr.softeng.team16.data.AuthCallback;
import gr.softeng.team16.data.DataRepository;
import gr.softeng.team16.data.LoadCallback;
import gr.softeng.team16.domain.Admin_Build;
import gr.softeng.team16.domain.Build;
import gr.softeng.team16.domain.CPU;
import gr.softeng.team16.domain.Cart;
import gr.softeng.team16.domain.Component;
import gr.softeng.team16.domain.Cooler;
import gr.softeng.team16.domain.GPU;
import gr.softeng.team16.domain.Memory;
import gr.softeng.team16.domain.Motherboard;
import gr.softeng.team16.domain.Order;
import gr.softeng.team16.domain.OrderLine;
import gr.softeng.team16.domain.PSU;
import gr.softeng.team16.domain.Storage;
import gr.softeng.team16.domain.Tower;

public class LocalRepository implements DataRepository {

    private List<Tower> towerList;
    private List<Motherboard> motherboardList;
    private List<CPU> cpuList;
    private List<GPU> gpuList;
    private List<Memory> memoryList;
    private List<Cooler> coolerList;
    private List<Storage> storageList;
    private List<PSU> psuList;
    private List<Build> buildList;

    private boolean isSuccessful = true;
    private String emailtoReturn = "test@email.com";

    private boolean usernameAvailable = true;

    private boolean authShouldSucceed = true;

    private boolean emailAvailable = true;

    private List<Build> buildsToReturn = new ArrayList<>();

    private List<OrderLine> productsToReturn = new ArrayList<>();

    private List<Order> orderList = new ArrayList<>();


    private boolean deleteShouldSucceed = true;

    public LocalRepository() {
        this.towerList = new ArrayList<>(BuildMother.generateTowerList());
        this.motherboardList = new ArrayList<>(BuildMother.generateMotherboardList());
        this.cpuList = new ArrayList<>(BuildMother.generateCPUList());
        this.gpuList = new ArrayList<>(BuildMother.generateGPUList());
        this.memoryList = new ArrayList<>(BuildMother.generateMemoryList());
        this.coolerList = new ArrayList<>(BuildMother.generateCoolerList());
        this.storageList = new ArrayList<>(BuildMother.generateStorageList());
        this.psuList = new ArrayList<>(BuildMother.generatePSUList());

        this.buildList = new ArrayList<>(BuildMother.generateBuildList());
    }

    public void setSuccessful(boolean successful) { isSuccessful = successful; }
    public void setEmailtoReturn(String email) { this.emailtoReturn = email; }
    public void setUsernameAvailable(boolean available) { this.usernameAvailable = available; }

    public void setAuthSuccessful(boolean status) { this.authShouldSucceed = status; }

    public void setEmailAvailable(boolean available) { this.emailAvailable = available; }

    public void setBuildsToReturn(List<Build> builds) {
        this.buildsToReturn = builds;
    }

    public void setCartToReturn(List<OrderLine> products) {this.productsToReturn= products;}

    public void setDeleteShouldSucceed(boolean status) {
        this.deleteShouldSucceed = status;
    }

    public  List<Order> getOrderList(){
        return orderList;
    }


    @Override
    public void getEmailByUsername(String username, LoadCallback<String> callback) {
        if (isSuccessful) {
            callback.onLoaded(emailtoReturn);
        } else {
            callback.onError(null);
        }
    }

    @Override
    public void loginUser(String username, String password, AuthCallback callback) {
        if (isSuccessful) {
            callback.onSuccess(username);
        } else {
            callback.onFailure("Login failed");
        }
    }

    @Override
    public void authenticateUser(String email, String password, AuthCallback callback) {
        if (authShouldSucceed) {
            callback.onSuccess(email);
        } else {
            callback.onFailure("Authentication failed");
        }
    }

    @Override
    public void checkUsernameAvailability(String username, LoadCallback<Boolean> callback) {
        if (isSuccessful) {
            callback.onLoaded(usernameAvailable);
        } else {
            callback.onError(null);
        }
    }

    @Override
    public void checkEmailAvailability(String email, LoadCallback<Boolean> callback) {
        if (isSuccessful) {
            callback.onLoaded(true);
        } else {
            callback.onError(null);
        }
    }

    @Override
    public void registerUser(String email, String password, Map<String, Object> userData, AuthCallback callback) {
        if (authShouldSucceed) {
            callback.onSuccess(email);
        } else {
            callback.onFailure("Registration failed");
        }
    }

    @Override
    public void getSavedBuilds(LoadCallback<List<Build>> callback) {
        if (isSuccessful) {
            callback.onLoaded(buildsToReturn);
        } else {
            callback.onError(null);
        }
    }

    @Override
    public void deleteBuild(String buildKey, AuthCallback callback) {
        if (deleteShouldSucceed) {
            callback.onSuccess("Deleted");
        } else {
            callback.onFailure("Delete failed");
        }
    }

    @Override
    public void addProductToCart(OrderLine orderline) {
        if (orderline == null) return;
        productsToReturn.add(orderline);

    }

    public List<OrderLine> getAddedProducts() {
        return productsToReturn;
    }

    @Override
    public void removeProductFromCart(OrderLine orderline) {

    }

    @Override
    public void getCart(LoadCallback<List<OrderLine>> callback) {
        if (isSuccessful) {
            callback.onLoaded(productsToReturn);
        } else {
            callback.onError(null);
        }

    }

    @Override
    public void saveBuild(Build build) {
        buildList.add(build);
    }

    @Override
    public void getComponents(String type, Class<? extends Component> clazz, LoadCallback<List<? extends Component>> callback) {
        switch (type) {
            case "Tower": callback.onLoaded(towerList); break;
            case "Motherboard": callback.onLoaded(motherboardList); break;
            case "CPU": callback.onLoaded(cpuList); break;
            case "GPU": callback.onLoaded(gpuList); break;
            case "Memory": callback.onLoaded(memoryList); break;
            case "Cooler": callback.onLoaded(coolerList); break;
            case "Storage": callback.onLoaded(storageList); break;
            case "PSU": callback.onLoaded(psuList); break;
            default: callback.onLoaded(null); break;
        }
    }

    @Override
    public void editComponent(Component component) {
        List<? extends Component> listToSearch = null;
        if (component instanceof Tower) listToSearch = towerList;
        else if (component instanceof Motherboard) listToSearch = motherboardList;
        else if (component instanceof CPU) listToSearch = cpuList;
        else if (component instanceof GPU) listToSearch = gpuList;
        else if (component instanceof Memory) listToSearch = memoryList;
        else if (component instanceof Cooler) listToSearch = coolerList;
        else if (component instanceof Storage) listToSearch = storageList;
        else if (component instanceof PSU) listToSearch = psuList;

        if (listToSearch != null) {
            for (int i = 0; i < listToSearch.size(); i++) {
                if (listToSearch.get(i).getId() == component.getId()) {
                    ((List<Component>) listToSearch).set(i, component);
                    return;
                }
            }
            ((List<Component>) listToSearch).add(component);
        }
    }

    @Override
    public void deleteComponent(Component component) {
        if (component instanceof Tower) towerList.removeIf(c -> c.getId() == component.getId());
        else if (component instanceof Motherboard) motherboardList.removeIf(c -> c.getId() == component.getId());
        else if (component instanceof CPU) cpuList.removeIf(c -> c.getId() == component.getId());
        else if (component instanceof GPU) gpuList.removeIf(c -> c.getId() == component.getId());
        else if (component instanceof Memory) memoryList.removeIf(c -> c.getId() == component.getId());
        else if (component instanceof Cooler) coolerList.removeIf(c -> c.getId() == component.getId());
        else if (component instanceof Storage) storageList.removeIf(c -> c.getId() == component.getId());
        else if (component instanceof PSU) psuList.removeIf(c -> c.getId() == component.getId());
    }

    @Override
    public void getOfferedBuilds(LoadCallback<List<Admin_Build>> callback) {
        if (isSuccessful) {
            List<Admin_Build> ab = new ArrayList<>();
            for (Build b: buildList) { ab.add(new Admin_Build(b)); }
            callback.onLoaded(ab);
        } else {
            callback.onError(null);
        }
    }

    @Override
    public void addOfferedBuild(Admin_Build build) {
        buildList.add(build);
    }

    @Override
    public void deleteOfferedBuild(Build build) {
        buildList.removeIf(b -> b.getId() == build.getId());
    }

    @Override
    public void clearUserCart(Cart cart) {
        if (cart != null) {
            cart.clearCart();
        }
        productsToReturn.clear();

    }

    @Override
    public String getUid() {
        return "1234";
    }

    @Override
    public void addOrder(Order order) {
        orderList.add(order);
    }

    @Override
    public void getOrders(LoadCallback<List<Order>> callback) {
        if (isSuccessful) {
            callback.onLoaded(new ArrayList<>(orderList));
        } else {
            callback.onError(null);
        }
    }

    @Override
    public void getOrderNextId(LoadCallback<Integer> callback) {
        if (isSuccessful) {
            callback.onLoaded(orderList.size() + 1);
        } else {
            callback.onError(null);
        }
    }

    @Override
    public void acceptOrder(Order order) {
        if (order == null) return;
        for (Order o : orderList) {
            if (o.getOrderId() == order.getOrderId()) {
                o.setStatus("ACCEPTED");
                break;
            }
        }
    }

    public int getNumOfOrders() {
        return orderList.size();
    }


    public void clearOfferedBuilds() {
        buildList.clear();
    }
}

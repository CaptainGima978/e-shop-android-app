package gr.softeng.team16.data;

import java.util.List;
import java.util.Map;

import gr.softeng.team16.domain.*;

public interface DataRepository {

   /**
     * Callback interface for loading data asynchronously.
     * @param callback The type of data to be loaded.
     */
    void getEmailByUsername(String username, LoadCallback<String> callback);

    /**
     * Logs in a user with the given username and password.
     * @param username The username of the user.
     * @param password The password of the user.
     * @param callback A callback to handle the result.
     */
    void loginUser(String username, String password, AuthCallback callback);

    /**
     * Authenticates a user with the given email and password.
     * @param email The email of the user.
     * @param password The password of the user.
     * @param callback A callback to handle the result.
     */

    void authenticateUser(String email, String password, AuthCallback callback);


    /**
     * Checks if a username is available.
     * @param username The username to check.
     * @param callback A callback to handle the result.
     */
    void checkUsernameAvailability(String username, LoadCallback<Boolean> callback);

    /**
     * Checks if an email is available.
     * @param email The email to check.
     * @param callback A callback to handle the result.
     */
    void checkEmailAvailability(String email, LoadCallback<Boolean> callback);


    /**
     * Registers a new user with the given email, password, and additional user data.
     * @param email The email of the user.
     * @param password The password of the user.
     * @param userData Additional user data to store.
     * @param callback A callback to handle the result.
     */
    void registerUser(String email, String password, Map<String, Object> userData, AuthCallback callback);

    /**
     * Retrieves a list of saved builds from the repository.
     * @param callback A callback to handle the result.
     */
    void getSavedBuilds(LoadCallback<List<Build>> callback);

    /**
     * Deletes a saved build from the repository.
     * @param buildKey The key of the build to delete.
     * @param callback A callback to handle the result.
     */
    void deleteBuild(String buildKey, AuthCallback callback);


    /**
     * Adds a product to the cart.
     * @param orderline The product to add.
     */
    void addProductToCart(OrderLine orderline);

    /**
     * Removes a product from the cart.
     * @param orderline The product to remove.
     */
    void removeProductFromCart(OrderLine orderline);

    /**
     * Retrieves the current user's cart.
     * @param callback A callback to handle the result.
     */
    void getCart(LoadCallback<List<OrderLine>> callback);


    /**
     * Saves a build to the repository.
     * @param build The build to save.
     */
    void saveBuild(Build build);


    /**
     * Retrieves a list of components of a specific type and class.
     * @param type The type of components to retrieve.
     * @param clazz The class of components to retrieve.
     * @param callback A callback to handle the result.
     */
    void getComponents(String type, Class<? extends Component> clazz, LoadCallback<List<? extends Component>> callback);

    /**
     * Edits a component in the repository or creates a new one if it doesn't exist.
     * @param component The component to edit.
     */
    void editComponent(Component component);

    /**
     * Deletes a component from the repository.
     * @param component The component to delete.
     */
    void deleteComponent(Component component);

    /**
     * Retrieves a list of offered builds from the repository.
     * @param callback A callback to handle the result.
     */
    void getOfferedBuilds(LoadCallback<List<Admin_Build>> callback);

    /**
     * Adds an offered build to the repository.
     * @param build The build to add.
     */
    void addOfferedBuild(Admin_Build build);

    /**
     * Deletes an offered build from the repository.
     * @param build The build to delete.
     */
    void deleteOfferedBuild(Build build);

    /**
     * Clears the user's cart.
     * @param cart The cart to clear.
     */
    void clearUserCart (Cart cart);

    /**
     * Retrieves the user's ID.
     * @return The user's ID.
     */
    String getUid();

    /**
    * Adds an order to the repository.
    * @param order The order to add.
    */
    void addOrder(Order order);

    void getOrders(LoadCallback<List<Order>> callback);

    void getOrderNextId(LoadCallback<Integer> callback);

    void acceptOrder(Order order);

    //void getUserOrders(String userId, LoadCallback<List<Order>> callback);
    //void removeUserOrder(String userId,  String orderKey, AuthCallback callback);

    // getUserOrders(), removeUserOrder() didn't work the way we wanted unfortunately, so we commented them out.
}

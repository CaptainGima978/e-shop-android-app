package gr.softeng.team16.data;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import gr.softeng.team16.domain.*;

public class FirebaseRepository implements DataRepository{

    private static FirebaseRepository instance;
    private final DatabaseReference componentsRef;
    private final DatabaseReference offeredBuildsRef;
    private final DatabaseReference usersRef;

    private final DatabaseReference regref;

    private static int towerId = 15000;
    private static int motherboardId = 25000;
    private static int cpuId = 35000;
    private static int gpuId = 4500;
    private static int memoryId = 5500;
    private static int coolerId = 6500;
    private static int storageId = 7500;
    private static int psuId = 8500;
    private static int buildId = 90005;


    private FirebaseRepository() {
        componentsRef = FirebaseDatabase.getInstance().getReference("components");
        offeredBuildsRef = FirebaseDatabase.getInstance().getReference("offered_builds");

        usersRef = FirebaseDatabase.getInstance().getReference("users");
        regref = FirebaseDatabase.getInstance().getReference();
    }

    public static synchronized FirebaseRepository getInstance() {
        if (instance == null) {
            instance = new FirebaseRepository();
        }
        return instance;
    }

    /**
     * Searches the 'users' node for a duplicate username.
     * Provides live feedback on login credentials for a smoother user experience.
     * @param username username to verify
     */
    public void getEmailByUsername(String username, final LoadCallback<String> callback) {
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users");
        usersRef.orderByChild("username").equalTo(username)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            String email = "";
                            for (DataSnapshot child : snapshot.getChildren()) {
                                email = child.child("email").getValue(String.class);
                            }
                            callback.onLoaded(email);
                        } else {
                            callback.onError(null); // User not found
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        callback.onError(error);
                    }
                });
    }

    /**
     * Searches for a user by username, retrieves their email, and attempts to sign them in.
     * @param username The username provided by the user.
     * @param password The password provided by the user.
     * @param callback Callback to return success or failure.
     */
    public void loginUser(String username, String password, AuthCallback callback) {
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users");

        // Step 1: Query database to find the email associated with the username
        usersRef.orderByChild("username").equalTo(username).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.exists()) {
                    callback.onFailure("User not Found");
                    return;
                }

                String emailFound = "";
                for (DataSnapshot child : snapshot.getChildren()) {
                    emailFound = child.child("email").getValue(String.class);
                }

                if (emailFound != null && !emailFound.isEmpty()) {
                    // Step 2: Authenticate with Firebase Auth using the retrieved email
                    final String finalEmail = emailFound;
                    FirebaseAuth.getInstance().signInWithEmailAndPassword(finalEmail, password)
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    callback.onSuccess(finalEmail);
                                } else {
                                    callback.onFailure(task.getException() != null ?
                                            task.getException().getMessage() : "Authentication failed");
                                }
                            });
                } else {
                    callback.onFailure("Email not found for this user");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onFailure("Database Error: " + error.getMessage());
            }
        });
    }

    /**
     * Authenticates a user with email and password using Firebase Auth.
     * @param email The user's email.
     * @param password The user's password.
     * @param callback The callback to handle success or failure.
     */
    @Override
    public void authenticateUser(String email, String password, AuthCallback callback) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Return the email on success to check for admin status in presenter
                        callback.onSuccess(email);
                    } else {
                        // Return the error message on failure
                        String errorMsg = task.getException() != null ?
                                task.getException().getMessage() : "Authentication failed";
                        callback.onFailure(errorMsg);
                    }
                });
    }

    /**
     * Checks if a username is already taken in the 'users' node.
     * @param username The username to check.
     * @param callback Callback to return availability status.
     */

    @Override
    public void checkUsernameAvailability(String username, LoadCallback<Boolean> callback) {
        regref.child("users").orderByChild("username").equalTo(username)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        callback.onLoaded(!snapshot.exists());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        callback.onError(error);
                    }
                });
    }

    /**
     * Checks if an email is already registered in the 'users' node.
     * @param email The email to check.
     * @param callback Callback to return availability status.
     */
    @Override
    public void checkEmailAvailability(String email, LoadCallback<Boolean> callback) {
        regref.child("users").orderByChild("email").equalTo(email)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        callback.onLoaded(!snapshot.exists());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        callback.onError(error);
                    }
                });
    }

    /**
     * Registers a new user with email and password, and saves additional user data in the database.
     * @param email The user's email.
     * @param password The user's password.
     * @param userData Additional user data to store in the database.
     * @param callback Callback to handle success or failure.
     */
    @Override
    public void registerUser(String email, String password, Map<String, Object> userData, AuthCallback callback) {

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult().getUser() != null) {
                        String userId = task.getResult().getUser().getUid();


                        regref.child("users").child(userId).setValue(userData)
                                .addOnCompleteListener(dbTask -> {
                                    if (dbTask.isSuccessful()) {
                                        callback.onSuccess(email);
                                    } else {
                                        callback.onFailure("Failed to save user data.");
                                    }
                                });

                        CustomerAccount account = new CustomerAccount();
                        account.setEmail(email);
                        account.setUsername(userData.get("username").toString());
                        account.setName(userData.get("firstName").toString() + " " + userData.get("lastName").toString());
                        usersRef.child(userId).child("account").setValue(account);


                    } else {
                        callback.onFailure(task.getException() != null ?
                                task.getException().getMessage() : "Registration failed");
                    }
                });
    }

    @Override
    public void clearUserCart(Cart cart) {
        String uid = FirebaseAuth.getInstance().getUid();
        if (uid == null) return;
        DatabaseReference userCartRef = usersRef.child(uid).child("cart");
        userCartRef.removeValue();
    }

    @Override
    public void addProductToCart(OrderLine orderline) {
        String uid = FirebaseAuth.getInstance().getUid();
        if (uid == null) return;
        DatabaseReference userCartRef = usersRef.child(uid).child("cart");
        userCartRef.child(String.valueOf(orderline.getProduct().getId())).setValue(orderline);
    }

    @Override
    public void removeProductFromCart(OrderLine orderline) {
        String uid = FirebaseAuth.getInstance().getUid();
        if (uid == null) return;
        DatabaseReference userCartRef = usersRef.child(uid).child("cart");
        userCartRef.child(String.valueOf(orderline.getProduct().getId())).removeValue();
    }

    @Override
    public void getCart(LoadCallback<List<OrderLine>> callback) {
        String uid = FirebaseAuth.getInstance().getUid();
        if (uid == null) return;
        DatabaseReference userCartRef = usersRef.child(uid).child("cart");
        userCartRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<OrderLine> lines = new ArrayList<>();
                for (DataSnapshot child : snapshot.getChildren()) {
                    OrderLine line = child.getValue(OrderLine.class);
                    if (line != null) {
                        lines.add(line);
                    }
                }
                callback.onLoaded(lines);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onError(error);
            }
        });
    }

    @Override
    public String getUid() {
        return FirebaseAuth.getInstance().getUid();
    }

    @Override
    public void acceptOrder(Order order) {
        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                    DataSnapshot ordersSnapshot = userSnapshot.child("orders");
                    if (ordersSnapshot.hasChild(order.getFirebaseKey())) {
                        ordersSnapshot.child(order.getFirebaseKey()).child("status").getRef().setValue("ACCEPTED");
                        return;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    @Override
    public void addOrder(Order order) {
        String uid = FirebaseAuth.getInstance().getUid();
        if (uid == null) return;

        usersRef.child(uid).child("account").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                CustomerAccount account = snapshot.getValue(CustomerAccount.class);
                order.setCustomerAccount(account);
                getOrderNextId(new LoadCallback<Integer>() {
                    @Override
                    public void onLoaded(Integer result) {

                        order.setOrderId(result);

                    }
                    @Override
                    public void onError(DatabaseError error) {
                    }
                });

                DatabaseReference orderRef = usersRef.child(uid).child("orders").push();
                order.setFirebaseKey(orderRef.getKey());
                orderRef.setValue(order);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    @Override
    public void getOrders(LoadCallback<List<Order>> callback) {
        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Order> allOrders = new ArrayList<>();
                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                    DataSnapshot ordersSnapshot = userSnapshot.child("orders");
                    for (DataSnapshot orderSnapshot : ordersSnapshot.getChildren()) {
                        Order order = orderSnapshot.getValue(Order.class);
                        if (order != null) {
                            order.setFirebaseKey(orderSnapshot.getKey());
                            allOrders.add(order);
                        }
                    }
                }
                callback.onLoaded(allOrders);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onError(error);
            }
        });
    }

    @Override
    public void getOrderNextId(LoadCallback<Integer> callback) {
        DatabaseReference orderNextIdRef = regref.child("system").child("order_next_id");
        orderNextIdRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Integer currentId = snapshot.getValue(Integer.class);
                if (currentId != null) {
                    orderNextIdRef.setValue(currentId + 1);
                    callback.onLoaded(currentId);
                } else {
                    callback.onError(null);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onError(error);
            }
        });

    }

    /**
     * @Override
    public void getUserOrders(String userId, LoadCallback<List<Order>> callback) {
        usersRef.child(userId).child("orders").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Order> orders = new ArrayList<>();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Order order = ds.getValue(Order.class);
                    if (order != null) {
                        order.setFirebaseKey(ds.getKey());
                        orders.add(order);
                    }
                }
                callback.onLoaded(orders);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onError(error);
            }
        });
    }

    @Override
    public void removeUserOrder(String userId, String orderKey, AuthCallback callback) {
        usersRef.child(userId).child("orders").child(orderKey).removeValue()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        callback.onSuccess("Order removed.");
                    } else {
                        callback.onFailure("Failed to remove order.");
                    }
                });
    }
    */
    // getUserOrders(), removeUserOrder() didn't work the way we wanted unfortunately, so we commented them out.



    @Override
    public void saveBuild(Build build) {
        getNextId("build", build);

        String uid = FirebaseAuth.getInstance().getUid();
        if (uid == null) return;

        DatabaseReference userSavedRef = usersRef.child(uid).child("saved_builds");
        DatabaseReference newBuildRef = userSavedRef.push();
        build.setFirebaseKey(newBuildRef.getKey());

        newBuildRef.setValue(build);
    }


    /**
     * Retrieves saved builds for the current user.
     * @param callback Callback to return the list of saved builds or an error.
     */
    @Override
    public void getSavedBuilds(LoadCallback<List<Build>> callback) {
        String uid = FirebaseAuth.getInstance().getUid();
        if (uid == null) return;

        usersRef.child(uid).child("saved_builds").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Build> builds = new ArrayList<>();
                for (DataSnapshot child : snapshot.getChildren()) {
                    Build build = child.getValue(Build.class);
                    if (build != null) {
                        build.setFirebaseKey(child.getKey());
                        builds.add(build);
                    }
                }
                callback.onLoaded(builds);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onError(error);
            }
        });
    }

    /**
     * Deletes a saved build for the current user.
     * @param buildKey The key of the build to delete.
     * @param callback Callback to handle success or failure.
     */
    @Override
    public void deleteBuild(String buildKey, AuthCallback callback) {
        usersRef.child("saved_builds").child(buildKey).removeValue()
                .addOnSuccessListener(aVoid -> callback.onSuccess("Deleted"))
                .addOnFailureListener(e -> callback.onFailure(e.getMessage()));
    }



    public void getComponents(String type, Class<? extends Component> clazz, final LoadCallback<List<? extends Component>> callback) {
        componentsRef.child(type.toLowerCase())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        List<Component> list = new ArrayList<>();
                        for (DataSnapshot child : snapshot.getChildren()) {
                            Component component = child.getValue(clazz);
                            if (component != null) {
                                list.add(component);
                            }
                        }
                        callback.onLoaded(list);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        callback.onError(error);
                    }
                });
    }

    @Override
    public void editComponent(Component component) {
        componentsRef.child(component.getClass().getSimpleName().toLowerCase())
                .child(String.valueOf(component.getId())).setValue(component);
    }

    @Override
    public void deleteComponent(Component component) {
        componentsRef.child(component.getClass().getSimpleName().toLowerCase())
                .child(String.valueOf(component.getId())).removeValue();
    }

    @Override
    public void getOfferedBuilds(final LoadCallback<List<Admin_Build>> callback) {
        offeredBuildsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Admin_Build> list = new ArrayList<>();
                for (DataSnapshot child : snapshot.getChildren()) {
                    Admin_Build build = (Admin_Build) mapSnapshotToBuild(child);
                    if (build != null) {
                        list.add(build);
                    }
                }
                callback.onLoaded(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onError(error);
            }
        });
    }

    @Override
    public void addOfferedBuild(Admin_Build build) {
        getNextId("build", build);
        offeredBuildsRef.child(String.valueOf(build.getId())).setValue(build);
    }

    @Override
    public void deleteOfferedBuild(Build build) {
        offeredBuildsRef.child(String.valueOf(build.getId())).removeValue();
    }


    /**
     * Helper method to map a DataSnapshot to a Build object
     * @param snapshot The DataSnapshot to map
     * @return The mapped Build object
     */
    public Build mapSnapshotToBuild(DataSnapshot snapshot) {
        if (!snapshot.exists()) return null;

        // 1. Initialize the basic Build object
        Admin_Build build = new Admin_Build(snapshot.child("name").getValue(String.class));
        // 2. Map basic fields
        build.setId(snapshot.child("id").getValue(Integer.class));
        build.setPrice(snapshot.child("price").getValue(Double.class));
        build.setDiscountPrice(snapshot.child("discountPrice").getValue(Double.class));
        if (snapshot.child("onDiscount").getValue(Boolean.class)) {
            build.setOnDiscount();
        }
        else {
            build.setOffDiscount();
        }
        build.setCompleted(snapshot.child("completed").getValue(Boolean.class));
        build.setFirebaseKey(snapshot.getKey());

        // 3. Map complex single objects
        if (snapshot.hasChild("tower")) {
            build.addTower(snapshot.child("tower").getValue(Tower.class));
        }
        if (snapshot.hasChild("motherboard")) {
            build.addMotherboard(snapshot.child("motherboard").getValue(Motherboard.class));
        }
        // 4. Map the PeripheralComponents Map manually to preserve Subclasses
        DataSnapshot pcSnapshot = snapshot.child("peripheralComponents");
        // CPU
        if (pcSnapshot.hasChild("CPU")) {
            for (DataSnapshot child : pcSnapshot.child("CPU").getChildren()) {
                CPU cpu = child.getValue(CPU.class);
                build.addPeripheralComponent(cpu);
            }
        }
        // GPU
        if (pcSnapshot.hasChild("GPU")) {
            for (DataSnapshot child : pcSnapshot.child("GPU").getChildren()) {
                GPU gpu = child.getValue(GPU.class);
                build.addPeripheralComponent(gpu);
            }
        }
        // Memory
        if (pcSnapshot.hasChild("Memory")) {
            for (DataSnapshot child : pcSnapshot.child("Memory").getChildren()) {
                Memory ram = child.getValue(Memory.class);
                build.addPeripheralComponent(ram);
            }
        }
        // Storage
        if (pcSnapshot.hasChild("Storage")) {
            for (DataSnapshot child : pcSnapshot.child("Storage").getChildren()) {
                Storage storage = child.getValue(Storage.class);
                build.addPeripheralComponent(storage);
            }
        }
        // PSU
        if (pcSnapshot.hasChild("PSU")) {
            for (DataSnapshot child : pcSnapshot.child("PSU").getChildren()) {
                PSU psu = child.getValue(PSU.class);
                build.addPeripheralComponent(psu);
            }
        }
        // Cooler
        if (pcSnapshot.hasChild("Cooler")) {
            for (DataSnapshot child : pcSnapshot.child("Cooler").getChildren()) {
                Cooler cooler = child.getValue(Cooler.class);
                build.addPeripheralComponent(cooler);
            }
        }

        return build;
    }

    private void getNextId(String type, Product product) {
        regref.child("system").child(type + "_id").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Integer currentId = snapshot.getValue(Integer.class);
                if (currentId != null) {
                    regref.child("system").child(type + "_id").setValue(currentId + 1);
                    product.setId(currentId);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }



}

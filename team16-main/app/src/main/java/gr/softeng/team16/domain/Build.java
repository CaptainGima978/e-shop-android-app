package gr.softeng.team16.domain;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.*;

public class Build extends Product implements Serializable {

    private static int nextId = 90000;

    private int wattDemand;
    private int totalMemory;
    private int totalStorage;
    private boolean completed;
    private Tower tower;
    private Motherboard motherboard;
    private HashMap<String, ArrayList<PeripheralComponent>> peripheralComponents;
    private final HashSet<Port> providedPorts;
    private final HashSet<Port> requiredPorts;
    private HashSet<Port> unmatchedPorts;

    private String firebaseKey;

    public Build() {

        this.peripheralComponents = new HashMap<>();


        this.peripheralComponents.put("CPU", new ArrayList<>());
        this.peripheralComponents.put("GPU", new ArrayList<>());
        this.peripheralComponents.put("Memory", new ArrayList<>());
        this.peripheralComponents.put("Storage", new ArrayList<>());
        this.peripheralComponents.put("PSU", new ArrayList<>());
        this.peripheralComponents.put("Cooler", new ArrayList<>());


        this.providedPorts = new HashSet<>();
        this.requiredPorts = new HashSet<>();
        this.unmatchedPorts = new HashSet<>();
    }

    public Build(String name) {
        super(nextId++, name, 0);

        this.wattDemand = 0;
        this.completed = false;

        this.peripheralComponents = new HashMap<>(
            Map.of(
                "CPU", new ArrayList<>(),
                "GPU", new ArrayList<>(),
                "Memory", new ArrayList<>(),
                "Storage", new ArrayList<>(),
                "PSU", new ArrayList<>(),
                "Cooler", new ArrayList<>()
            )
        );

        this.providedPorts = new HashSet<>();
        this.requiredPorts = new HashSet<>();
        this.unmatchedPorts = new HashSet<>();
    }

    //copy constructor
    public Build(Build original) {
        super(original);
        this.wattDemand = original.wattDemand;
        this.totalMemory = original.totalMemory;
        this.totalStorage = original.totalStorage;
        this.completed = original.completed;
        this.tower = original.tower;
        this.motherboard = original.motherboard;
        this.peripheralComponents = original.peripheralComponents;
        this.providedPorts = original.providedPorts;
        this.requiredPorts = original.requiredPorts;
        this.unmatchedPorts = original.unmatchedPorts;
    }

    /** Method to add a tower to the build
     * @param tower The tower to be added
     * @return true if the tower was added successfully, false otherwise
     */
    public boolean addTower(Tower tower){
        if(this.tower != null){
            return false;
        }
        this.tower = tower;

        updateBuildState();
        return true;
    }

    /** Method to remove the tower from the build
     * @return true if the tower was removed successfully, false otherwise
     */
    public boolean removeTower(){
        if(this.tower != null) {
            this.tower = null;

            updateBuildState();
            return true;
        }
        return false;
    }

    /** Method to add a motherboard to the build
     * @param motherboard The motherboard to be added
     * @return true if the motherboard was added successfully, false otherwise
     */
    public boolean addMotherboard(Motherboard motherboard){
        if(this.motherboard != null) {
            return false;
        }

        this.motherboard = motherboard;

        updateBuildState();
        return true;
    }

    /** Method to remove the motherboard from the build
     * @return true if the motherboard was removed successfully, false otherwise
     */
    public boolean removeMotherboard(){
        if(this.motherboard != null) {
            this.motherboard = null;

            updateBuildState();
            return true;
        }
        return false;
    }

    /** Method to add a peripheral component to the build
     * @param component The peripheral component to be added
     * @return true if the component was added successfully, false otherwise
     */
    public boolean addPeripheralComponent(PeripheralComponent component){

        // Add component to the appropriate category
        String key ;
        int category = component.getId() / 10000;

        switch(category){
            case 3:
                key = "CPU";
                if (!peripheralComponents.get(key).isEmpty()) {
                    return false; // Only one CPU allowed
                }
                break;
            case 4:
                key = "GPU";
                break;
            case 5:
                key = "Memory";
                break;
            case 6:
                key = "Cooler";
                if (!peripheralComponents.get(key).isEmpty()) {
                    return false;
                }

                if (peripheralComponents.get("CPU").isEmpty()) {
                    return false; // CPU must be present
                }

                CPU cpu = (CPU) peripheralComponents.get("CPU").get(0);
                if (cpu.getCoolerIn()) return false;

                String cpuSocket = cpu.getSocket();
                List<String> supportedSockets = ((Cooler) component).getSocket();
                boolean compatible = false;
                for (String socket : supportedSockets) {
                    if (socket.equals(cpuSocket)) {
                        compatible = true;
                        break;
                    }
                }
                if (!compatible) return false;

                break;

            case 7:
                key = "Storage";
                break;
            case 8:
                key = "PSU";
                if (!peripheralComponents.get(key).isEmpty()) {
                    return false; // Only one PSU allowed
                }
                break;
            default:
                return false;
        }

        // Add the component and update watt demand
        this.peripheralComponents.get(key).add(component);

        updateBuildState();
        return true;
    }

    /** Method to remove a peripheral component from the build
     * @param component The peripheral component to be removed
     * @return true if the component was removed successfully, false otherwise
     */
    public boolean removePeripheralComponent(PeripheralComponent component){
        String key ;
        int category = component.getId() / 10000;

        // Determine the category key
        switch(category) {
            case 3:
                key = "CPU";
                // Also remove cooler if present
                List<PeripheralComponent> coolers = peripheralComponents.get("Cooler");
                if (!coolers.isEmpty()) {
                    Cooler cooler = (Cooler) coolers.get(0);
                    if (!removePeripheralComponent(cooler)) return false;
                }

                break;
            case 4:
                key = "GPU";
                break;
            case 5:
                key = "Memory";
                break;
            case 6:
                key = "Cooler";
                break;
            case 7:
                key = "Storage";
                break;
            case 8:
                key = "PSU";
                break;
            default:
                return false;
        }

        // Attempt to remove the component and update watt demand if successful
        boolean removed = this.peripheralComponents.get(key).remove(component);

        if(removed){
            updateBuildState();
            return true;
        }
        return false;
    }

    private void checkCompletion() {
        this.completed = true;
        // components presence check
        if (this.tower == null || this.motherboard == null) {
            this.completed = false;
            return;
        }
        for (String key: peripheralComponents.keySet()) {
            if (key.equals("Cooler") && getCpu() != null && getCpu().getCoolerIn()) continue;
            if (key.equals("PSU") && tower.getPowerSupply() > 0) continue;
            if (peripheralComponents.get(key).isEmpty()) {
                this.completed = false;
                break;
            }
        }
        //Ports matching check
        if (!unmatchedPorts.isEmpty()) {
            this.completed = false;
            return;
        }
        // PSU sufficient check
        if (!isPsuSufficient()) {
            this.completed = false;
            return;
        }
        // check memory support constraint

        List<PeripheralComponent> cpus = peripheralComponents.get("CPU");
        if (!cpus.isEmpty()) {
            CPU cpu = (CPU) cpus.get(0);
            if (totalMemory > cpu.getMemorySupport()) {
                this.completed = false;
                return;
            }
        }

        // check gpu length constraint
        for (PeripheralComponent gpu : peripheralComponents.get("GPU")) {
            int gpuLength = ((GPU) gpu).getLength();
            if (tower != null && gpuLength > tower.getMaxGpuLength()) {
                this.completed = false;
                break;
            }
        }
    }

    private void updateBuildState() {
        rebuildPorts();
        portsMatching();

        updateWattDemand();
        updatePrice();

        totalMemory = 0;
        for (PeripheralComponent ram: peripheralComponents.get("Memory")) {
            totalMemory += ((Memory) ram).getCapacity() * ((Memory) ram).getModules();
        }
        totalStorage = 0;
        for (PeripheralComponent disk: peripheralComponents.get("Storage")) {
            totalStorage += ((Storage) disk).getCapacity();
        }

        checkCompletion();
    }
    private void rebuildPorts() {
        providedPorts.clear();
        requiredPorts.clear();

        if (tower != null) {
            providedPorts.addAll(Port.provide(tower));
            requiredPorts.addAll(Port.require(tower));
        }
        if (motherboard != null) {
            providedPorts.addAll(Port.provide(motherboard));
            requiredPorts.addAll(Port.require(motherboard));
        }
        for (ArrayList<PeripheralComponent> list : peripheralComponents.values()) {
            for (PeripheralComponent pc : list) {
                mergePorts(providedPorts, Port.provide(pc));
                mergePorts(requiredPorts, Port.require(pc));
            }
        }
    }

    private void mergePorts(Set<Port> target, Set<Port> incoming) {
        for (Port port : incoming) {
            if (!target.add(port)) {
                for (Port p : target) {
                    if (p.equals(port)) {
                        p.setCount(p.getCount() + port.getCount());
                        break;
                    }
                }
            }
        }
    }


    /**
     * Helper method to update the watt demand of the build
     */
    private void updateWattDemand(){
        int totalWatt = 0;

        if(this.motherboard != null){
            totalWatt += 40; // Average wattage for motherboard
        }

        for(String key: peripheralComponents.keySet()){
            for(PeripheralComponent component : peripheralComponents.get(key)) {
                switch (key) {
                    case "CPU":
                        totalWatt += ((CPU) component).getTdp();
                        break;
                    case "GPU":
                        totalWatt += ((GPU) component).getTdp();
                        break;
                    case "Storage":
                        totalWatt += 10; // Average wattage for storage drives
                        break;
                    case "Memory":
                        totalWatt += 5 * ((Memory) component).getModules(); // Average wattage per RAM module
                        break;
                    case "Cooler":
                        totalWatt += 5; // Average wattage for coolers
                        break;
                }
            }
        }
        this.wattDemand = totalWatt;
    }

    /**
     * Helper method to check if PSU wattage is sufficient for the build
     * @return true if PSU wattage is sufficient, false otherwise
     */
    public boolean isPsuSufficient(){
        int psuWattage = 0;

        if(tower!=null && tower.getPowerSupply()>0){
            psuWattage += tower.getPowerSupply();
        }
        else {
            for (PeripheralComponent component : peripheralComponents.get("PSU")) {
                psuWattage += ((PSU) component).getWattage();
            }
        }
        return psuWattage >= this.wattDemand;
    }

    /** Helper method to check if all required ports are matched by provided ports
     */
    private void portsMatching() {
        unmatchedPorts = new HashSet<>(requiredPorts);
        for (Port required : requiredPorts) {
            for (Port provided : providedPorts) {
                if (provided.covers(required)) {
                    unmatchedPorts.remove(required);
                    break;
                }
                // Partial match: same port but insufficient count
                else if (provided.equals(required) && provided.getCount() < required.getCount()) {
                    int remainingCount = required.getCount() - provided.getCount();
                    Port remainingPort = new Port(required.getName(), required.getType(), remainingCount);
                    unmatchedPorts.remove(required);
                    unmatchedPorts.add(remainingPort);
                    break;
                }
            }
        }
    }

    /** Helper method to update the total price of the build
     */
    private void updatePrice() {
        double totalPrice = 0;

        if (tower != null) {
            totalPrice += tower.getPrice();
        }
        if (motherboard != null) {
            totalPrice += motherboard.getPrice();
        }
        for (ArrayList<PeripheralComponent> components : peripheralComponents.values()) {
            for (PeripheralComponent component : components) {
                totalPrice += component.getPrice();
            }
        }
        this.setPrice(totalPrice);
    }

    public double getFinalPrice() {
        return getPrice();
    }


    public Motherboard getMotherboard() {
        return motherboard;
    }

    public Tower getTower() {
        return tower;
    }

    public HashMap<String, ArrayList<PeripheralComponent>> getPeripheralComponents() {
        return peripheralComponents;
    }

    @Exclude
    public CPU getCpu() {
        if (peripheralComponents.get("CPU").isEmpty()) {
            return null;
        }
        return (CPU) peripheralComponents.get("CPU").get(0);
    }

    @Exclude
    public ArrayList<GPU> getGpu() {
        ArrayList<GPU> gpus = new ArrayList<>();
        for (PeripheralComponent component : peripheralComponents.get("GPU")) {
            gpus.add((GPU) component);
        }
        return gpus;
    }

    @Exclude
    public ArrayList<Memory> getMemory() {
        ArrayList<Memory> memories = new ArrayList<>();
        for (PeripheralComponent component : peripheralComponents.get("Memory")) {
            memories.add((Memory) component);
        }
        return memories;
    }

    @Exclude
    public ArrayList<Storage> getStorage() {
        ArrayList<Storage> storages = new ArrayList<>();
        for (PeripheralComponent component : peripheralComponents.get("Storage")) {
            storages.add((Storage) component);
        }
        return storages;
    }

    @Exclude
    public PSU getPsu() {
        if (peripheralComponents.get("PSU").isEmpty()) {
            return null;
        }
        return (PSU) peripheralComponents.get("PSU").get(0);
    }

    @Exclude
    public Cooler getCooler() {
        if (peripheralComponents.get("Cooler").isEmpty()) {
            return null;
        }
        return (Cooler) peripheralComponents.get("Cooler").get(0);
    }

    public int getTotalStorage() {
        return totalStorage;
    }

    public int getTotalMemory() {
        return totalMemory;
    }

    @Exclude
    public HashSet<Port> getUnmatchedPorts() {
        return unmatchedPorts;
    }

    @Exclude
    public HashSet<Port> getProvidedPorts() {
        return providedPorts;
    }

    @Exclude
    public HashSet<Port> getRequiredPorts() {
        return requiredPorts;
    }

    public int getWattDemand() {
        return wattDemand;
    }

    public boolean isCompleted() {
        return completed;
    }

    public String getFirebaseKey() {
        return firebaseKey;
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public double getPrice() {
        return super.getPrice();
    }

    @Override
    public int getId() {
        return super.getId();
    }

    @Override
    public String getImg() {
        if (tower != null) {
            return tower.getImg();
        }
        return super.getImg();
    }


    @Override
    public void setName(String name) {
        super.setName(name);
    }

    @Override
    public void setPrice(double price) {
        super.setPrice(price);
    }

    @Override
    public void setId(int id) {
        super.setId(id);
    }
    public void setTower(Tower tower) { this.tower = tower; }
    public void setMotherboard(Motherboard motherboard) { this.motherboard = motherboard; }
    public void setPeripheralComponents(HashMap<String, ArrayList<PeripheralComponent>> peripheralComponents) {
        this.peripheralComponents = peripheralComponents;
    }
    public void setWattDemand(int wattDemand) { this.wattDemand = wattDemand; }
    public void setTotalMemory(int totalMemory) { this.totalMemory = totalMemory; }
    public void setTotalStorage(int totalStorage) { this.totalStorage = totalStorage; }
    public void setCompleted(boolean completed) { this.completed = completed; }

    @Exclude
    public void setUnmatchedPorts(HashSet<Port> unmatchedPorts) {
        this.unmatchedPorts = unmatchedPorts;
    }

    @Exclude
    public void setProvidedPorts(HashSet<Port> providedPorts) {
        this.providedPorts.clear();
        this.providedPorts.addAll(providedPorts);
    }

    @Exclude
    public void setRequiredPorts(HashSet<Port> requiredPorts) {
        this.requiredPorts.clear();
        this.requiredPorts.addAll(requiredPorts);
    }

    public void setFirebaseKey(String firebaseKey) {
        this.firebaseKey = firebaseKey;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Build)) return false;
        Build other = (Build) o;

        return Objects.equals(getName(), other.getName()) &&
                Objects.equals(tower, other.tower) &&
                Objects.equals(motherboard, other.motherboard) &&
                Objects.equals(peripheralComponents, other.peripheralComponents);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), tower, motherboard, peripheralComponents);
    }

    @NonNull
    @Override
    public String toString() {
        String s = "Original Price: " + String.format(Locale.US, "%.2f", getPrice()) + "€\n";
        s += "Memory: " + totalMemory + "GB — Storage: " + totalStorage + "TB\n";
        s += "Energy Consumption: " + wattDemand + "Watt";

        return s;
    }
}

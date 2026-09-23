package gr.softeng.team16.domain;

import androidx.annotation.NonNull;

public class Memory extends PeripheralComponent {

    private String type; // DDR4, DDR5
    private int capacity; // GB
    private int modules;

    public Memory(int id, String name, String manufacturer, double price, String img, String type, int capacity, int modules) {
        super(id, name, manufacturer, price, img);
        this.type = type;
        this.capacity = capacity;
        this.modules = modules;

        providedPorts = Port.provide(this);
        requiredPorts = Port.require(this);
    }

    public Memory() {}

    public String getType() {
        return type;
    }
    public int getCapacity() {
        return capacity;
    }
    public int getModules() {
        return modules;
    }

    public void setType(String type) {
        this.type = type;
    }
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
    public void setModules(int modules) {
        this.modules = modules;
    }

    @NonNull
    @Override
    public String toString() {
        return type + " " + capacity + "GB " + modules + "-module";
    }

}

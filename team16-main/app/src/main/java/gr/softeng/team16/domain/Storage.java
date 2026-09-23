package gr.softeng.team16.domain;

import androidx.annotation.NonNull;

public class Storage extends PeripheralComponent {

    private int capacity; // TB
    private String type; // SSD, 7200 RPM, 5 400 RPM (HDD)
    private String driveFormFactor; //  2.5", M.2, 3.5"

    public Storage(int id, String name, String manufacturer, double price, String img, int capacity, String type, String driveFormFactor) {
        super(id, name, manufacturer, price, img);
        this.capacity = capacity;
        this.type = type;
        this.driveFormFactor = driveFormFactor;

        providedPorts = Port.provide(this);
        requiredPorts = Port.require(this);
    }

    public Storage() {}

    public int getCapacity() {
        return capacity;
    }
    public String getType() {
        return type;
    }
    public String getDriveFormFactor() {
        return driveFormFactor;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
    public void setType(String type) {
        this.type = type;
    }
    public void setFormFactor(String driveFormFactor) {
        this.driveFormFactor = driveFormFactor;
    }


    @NonNull
    @Override
    public String toString() {
        String t = type;
        if (!type.equals("SSD")) {
            t = "HDD";
        }
        return capacity + "TB " + t + " " + driveFormFactor;
    }

}

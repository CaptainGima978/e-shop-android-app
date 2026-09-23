package gr.softeng.team16.domain;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Tower extends Component {

    private List<String> formFactor; // List of supported motherboard form factors
    private int powerSupply; // Watt ( 0 if no power supply included )
    private int maxGpuLength; // mm
    private HashMap<String, Integer> driveBays; // [2.5" bays, 3.5" bays]
    private int expansionSlots;
    private String dimensions; // HxWxD in mm

    public Tower(int id, String name, String manufacturer, double price, String img,
                String[] formFactor, int powerSupply, int maxGpuLength,
                int[] driveBays, int expansionSlots, String dimensions) {
        super(id, name, manufacturer, price, img);
        this.formFactor = Arrays.asList(formFactor);
        this.powerSupply = powerSupply;
        this.maxGpuLength = maxGpuLength;
        this.driveBays = new HashMap<>();
        this.driveBays.put("2_5", driveBays[0]);
        this.driveBays.put("3_5", driveBays[1]);
        this.expansionSlots = expansionSlots;
        this.dimensions = dimensions;

        providedPorts = Port.provide(this);
        requiredPorts = Port.require(this);
    }

    public Tower() {}

    public List<String> getFormFactor() {
        return formFactor;
    }
    public int getPowerSupply() {
        return powerSupply;
    }
    public int getMaxGpuLength() {
        return maxGpuLength;
    }
    public HashMap<String, Integer> getDriveBays() {
        return driveBays;
    }
    public Integer getDriveBays(String form) {
        return driveBays.get(form);
    }
    public int getExpansionSlots() {
        return expansionSlots;
    }
    public String getDimensions() {
        return dimensions;
    }


    public void setFormFactor(List<String> formFactor) {
        this.formFactor = formFactor;
    }
    public void setPowerSupply(int powerSupply) {
        this.powerSupply = powerSupply;
    }
    public void setMaxGpuLength(int maxGpuLength) {
        this.maxGpuLength = maxGpuLength;
    }
    public void setDriveBays(HashMap<String, Integer> driveBays) {
        this.driveBays = driveBays;
    }
    public void setExpansionSlots(int expansionSlots) {
        this.expansionSlots = expansionSlots;
    }
    public void setDimensions(String dimensions) {
        this.dimensions = dimensions;
    }


    @NonNull
    @Override
    public String toString() {
        String s = "Form Factor: " + String.join(", ", formFactor) + "\n";
        if (powerSupply > 0) {
            s += "Power Supply Included: " + powerSupply + "Watt\n";
        }

        return s;
    }
}

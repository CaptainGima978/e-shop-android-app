package gr.softeng.team16.domain;

import androidx.annotation.NonNull;

public class PSU extends PeripheralComponent {

    private int wattage; // Watt

    public PSU(int id, String name, String manufacturer, double price, String img, int wattage) {
        super(id, name, manufacturer, price, img);
        this.wattage = wattage;

        providedPorts = Port.provide(this);
        requiredPorts = Port.require(this);
    }

    public PSU() {}

    public int getWattage() {
        return wattage;
    }

    public void setWattage(int wattage) {
        this.wattage = wattage;
    }

    @NonNull
    @Override
    public String toString() {
        return wattage + "Watt";
    }

}

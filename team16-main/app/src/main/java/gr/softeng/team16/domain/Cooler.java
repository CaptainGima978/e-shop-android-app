package gr.softeng.team16.domain;

import androidx.annotation.NonNull;

import java.util.Arrays;
import java.util.List;

public class Cooler extends PeripheralComponent {

    private int rpm; // RPM
    private double noiseLevel; // dB
    private List<String> socket; // Supported CPU sockets
    private Boolean waterCooled; // Water Cooled or Air Cooled

    public Cooler(int id, String name, String manufacturer, double price, String img,
                  int rpm, double noiseLevel, String[] socket, Boolean waterCooled) {
        super(id, name, manufacturer, price, img);
        this.rpm = rpm;
        this.noiseLevel = noiseLevel;
        this.socket = Arrays.asList(socket);
        this.waterCooled = waterCooled;

        providedPorts = Port.provide(this);
        requiredPorts = Port.require(this);
    }

    public Cooler() {}

    public int getRpm() {
        return rpm;
    }
    public double getNoiseLevel() {
        return noiseLevel;
    }
    public List<String> getSocket() {
        return socket;
    }
    public Boolean getWaterCooled() {
        return waterCooled;
    }

    public void setRpm(int rpm) {
        this.rpm = rpm;
    }
    public void setNoiseLevel(double noiseLevel) {
        this.noiseLevel = noiseLevel;
    }
    public void setSocket(List<String> socket) {
        this.socket = socket;
    }
    public void setWaterCooled(Boolean waterCooled) {
        this.waterCooled = waterCooled;
    }

    @NonNull
    @Override
    public String toString() {
        String s = "Sockets: " + String.join(", ", socket) + "\n";
        s += "RPM: " + rpm + "\n";
        s += "Noise Level: " + noiseLevel + "\n";
        if (waterCooled) {
            s += "Water Cooled\n";
        }

        return s;
    }

}

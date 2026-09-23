package gr.softeng.team16.domain;

import androidx.annotation.NonNull;

public class CPU extends PeripheralComponent {
    private String socket; // socket type
    private int coreCount;
    private double clock; // GHz
    private double clockBoost; // GHz
    private int tdp; // Watt
    private int cache; // MB
    private int memorySupport; // GB
    private Boolean coolerIn; // cooler included

    public CPU(int id, String name, String manufacturer, double price, String img,
                       String socket, int coreCount, double clock, double clockBoost,
                       int tdp, int cache, int memorySupport, Boolean coolerIn) {
        super(id, name, manufacturer, price, img);
        this.socket = socket;
        this.coreCount = coreCount;
        this.clock = clock;
        this.clockBoost = clockBoost;
        this.tdp = tdp;
        this.cache = cache;
        this.memorySupport = memorySupport;
        this.coolerIn = coolerIn;

        providedPorts = Port.provide(this);
        requiredPorts = Port.require(this);
    }

    public CPU() {}

    public String getSocket() {
        return socket;
    }
    public int getCoreCount() {
        return coreCount;
    }
    public double getClock() {
        return clock;
    }
    public double getClockBoost() {
        return clockBoost;
    }
    public int getTdp() {
        return tdp;
    }
    public int getCache() {
        return cache;
    }
    public int getMemorySupport() {
        return memorySupport;
    }
    public Boolean getCoolerIn() {
        return coolerIn;
    }

    public void setSocket(String socket) {
        this.socket = socket;
    }
    public void setCoreCount(int coreCount) {
        this.coreCount = coreCount;
    }
    public void setClock(double clock) {
        this.clock = clock;
    }
    public void setClockBoost(double clockBoost) {
        this.clockBoost = clockBoost;
    }
    public void setTdp(int tdp) {
        this.tdp = tdp;
    }
    public void setCache(int cache) {
        this.cache = cache;
    }
    public void setMemorySupport(int memorySupport) {
        this.memorySupport = memorySupport;
    }
    public void setCoolerIn(Boolean coolerIn) {
        this.coolerIn = coolerIn;
    }


    @NonNull
    @Override
    public String toString() {
        String s = String.valueOf(coreCount) + "-core " + String.valueOf(clock) + "GHz\n";
        s += "Socket: " + socket + "\n";
        if (coolerIn) s += "! Cooler included\n";

        return s;
    }
}

package gr.softeng.team16.domain;

import androidx.annotation.NonNull;

public class GPU extends PeripheralComponent {

    private int memory; // GB
    private double clock; // MHz
    private double clockBoost; // MHz
    private int length; // mm
    private int tdp; // Watt
    private int expansionSlots; // Expansion Slots Required on Motherboard
    private int hdmiOut; // HDMI Outputs
    private int dpOut; // DisplayPort Outputs

    public GPU(int id, String name, String manufacturer, double price, String img,
               int memory, double clock, double clockBoost, int length,
               int tdp, int expansionSlots, int hdmiOut, int dpOut) {
        super(id, name, manufacturer, price, img);
        this.memory = memory;
        this.clock = clock;
        this.clockBoost = clockBoost;
        this.length = length;
        this.tdp = tdp;
        this.expansionSlots = expansionSlots;
        this.hdmiOut = hdmiOut;
        this.dpOut = dpOut;

        providedPorts = Port.provide(this);
        requiredPorts = Port.require(this);
    }

    public GPU() {}

    public int getMemory() {
        return memory;
    }
    public double getClock() {
        return clock;
    }
    public double getClockBoost() {
        return clockBoost;
    }
    public int getLength() {
        return length;
    }
    public int getTdp() {
        return tdp;
    }
    public int getExpansionSlots() {
        return expansionSlots;
    }
    public int getHdmiOut() {
        return hdmiOut;
    }
    public int getDpOut() {
        return dpOut;
    }

    public void setMemory(int memory) {
        this.memory = memory;
    }
    public void setClock(double clock) {
        this.clock = clock;
    }
    public void setClockBoost(double clockBoost) {
        this.clockBoost = clockBoost;
    }
    public void setLength(int length) {
        this.length = length;
    }
    public void setTdp(int tdp) {
        this.tdp = tdp;
    }
    public void setExpansionSlots(int expansionSlots) {
        this.expansionSlots = expansionSlots;
    }
    public void setHdmiOut(int hdmiOut) {
        this.hdmiOut = hdmiOut;
    }
    public void setDpOut(int dpOut) {
        this.dpOut = dpOut;
    }

    @NonNull
    @Override
    public String toString() {
        String s = memory + "GB, " + clock + "MHz\n";
        s += "Output: ";
        if (hdmiOut > 0) {
            s += hdmiOut + "xHDMI ";
        }
        if (dpOut > 0) {
            s += dpOut + "xDP ";
        }

        return s;
    }


}

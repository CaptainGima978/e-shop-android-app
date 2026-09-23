package gr.softeng.team16.domain;

import androidx.annotation.NonNull;

public class Motherboard extends Component {

    private String socket; // CPU socket type
    private String formFactor; // Form factor for case compatibility
    private String memoryType;
    private int memorySlots;
    private int m2Slots; // Number of M.2 slots
    private int pciSlots; // Number of PCIe x16 slots (GPU)
    private int sataSlots; // Number of SATA ports

    public Motherboard(int id, String name, String manufacturer, double price, String img,
                       String socket, String formFactor, String memoryType,
                       int memorySlots, int m2Slots, int pciSlots, int sataSlots) {
        super(id, name, manufacturer, price, img);
        this.socket = socket;
        this.formFactor = formFactor;
        this.memoryType = memoryType;
        this.memorySlots = memorySlots;
        this.m2Slots = m2Slots;
        this.pciSlots = pciSlots;
        this.sataSlots = sataSlots;

        providedPorts = Port.provide(this);
        requiredPorts = Port.require(this);
    }

    public Motherboard() {}

    public String getSocket() {
        return socket;
    }
    public String getFormFactor() {
        return formFactor;
    }
    public String getMemoryType() {
        return memoryType;
    }
    public int getMemorySlots() {
        return memorySlots;
    }
    public int getM2Slots() {
        return m2Slots;
    }
    public int getPciSlots() {
        return pciSlots;
    }
    public int getSataSlots() {
        return sataSlots;
    }

    public void setSocket(String socket) {
        this.socket = socket;
    }
    public void setFormFactor(String formFactor) {
        this.formFactor = formFactor;
    }
    public void setMemoryType(String memoryType) {
        this.memoryType = memoryType;
    }
    public void setMemorySlots(int memorySlots) {
        this.memorySlots = memorySlots;
    }
    public void setM2Slots(int m2Slots) {
        this.m2Slots = m2Slots;
    }
    public void setPciSlots(int pciSlots) {
        this.pciSlots = pciSlots;
    }
    public void setSataSlots(int sataSlots) {
        this.sataSlots = sataSlots;
    }


    @NonNull
    @Override
    public String toString() {
        String s = "Socket: " + socket + " Form Factor: " + formFactor + "\n";
        s += "Memory Type: " + memoryType + " Slots: " + memorySlots + "\n";

        return s;
    }

}

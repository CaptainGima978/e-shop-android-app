package gr.softeng.team16.domain;
import java.util.*;

public class Port {
    public enum Type {
        CPU_SOCKET, // CPU - Motherboard - Cooler
        FORM_FACTOR, // Tower - Motherboard
        EXPANSION, // Tower - GPU
        RAM_SLOT, // Motherboard - Memory
        PCIe_x16, // Motherboard - GPU
        M_2, // Motherboard - Storage SSD
        DRIVE_FORM_FACTOR, // Tower - Storage HDD/SSD
        SATA, // Motherboard - Storage HDD/SSD
        HDMI, // GPU
        DISPLAY_PORT // GPU
    };
    private String name;
    private Type type;
    private int count;

    public Port(String name, Type type) {
        this.name = name;
        this.type = type;
        this.count = 1;
    }

    public Port(String name, Type type, int count) {
        this.name = name;
        this.type = type;
        this.count = count;
    }

    public Port(){}

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Port port = (Port) o;
        return Objects.equals(name, port.name) && type == port.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type);
    }

    /**     * Checks if this port covers the required port
     * @param requiredPort The required port to check against
     * @return true if this port covers the required port, false otherwise
     */
    public boolean covers(Port requiredPort) {
        return this.equals(requiredPort) && this.count >= requiredPort.count;
    }


    /**     * Returns the set of ports provided by the given component
     * @param component The component to get the provided ports from
     * @return A set of ports provided by the component
     */
    public static HashSet<Port> provide(Component component) {
        HashSet<Port> providedPorts = new HashSet<>();
        switch(component.getId() / 10000) {
            // Tower
            case 1:
                for (String form: ((Tower) component).getFormFactor()) {
                    providedPorts.add(new Port(form, Type.FORM_FACTOR));
                }
                providedPorts.add(new Port("2.5", Type.DRIVE_FORM_FACTOR, ((Tower) component).getDriveBays("2_5")));
                providedPorts.add(new Port("3.5", Type.DRIVE_FORM_FACTOR, ((Tower) component).getDriveBays("3_5")));
                providedPorts.add(new Port("Expansion", Type.EXPANSION, ((Tower) component).getExpansionSlots()));
                break;

            // Motherboard
            case 2:
                providedPorts.add(new Port(((Motherboard) component).getSocket(), Type.CPU_SOCKET));
                providedPorts.add(new Port(((Motherboard) component).getMemoryType(), Type.RAM_SLOT, ((Motherboard) component).getMemorySlots()));
                providedPorts.add(new Port("M2", Type.M_2, ((Motherboard) component).getM2Slots()));
                providedPorts.add(new Port("SATA", Type.SATA, ((Motherboard) component).getSataSlots()));
                providedPorts.add(new Port("PCIe_x16", Type.PCIe_x16, ((Motherboard) component).getPciSlots()));
                break;

            // GPU
            case 4:
                providedPorts.add(new Port("HDMI", Type.HDMI, ((GPU) component).getHdmiOut()));
                providedPorts.add(new Port("DISPLAY_PORT", Type.DISPLAY_PORT, ((GPU) component).getDpOut()));
                break;

            default:
                break;
        }

        return providedPorts;
    }

    /**     * Returns the set of ports required by the given component
     * @param component The component to get the required ports from
     * @return A set of ports required by the component
     */
    public static HashSet<Port> require(Component component) {
        HashSet<Port> requiredPorts= new HashSet<>();
        switch(component.getId() / 10000) {
            // Tower
            case 1:
                break;

            // Motherboard
            case 2:
                requiredPorts.add(new Port(((Motherboard) component).getFormFactor(), Type.FORM_FACTOR));
                break;

            // CPU
            case 3:
                requiredPorts.add(new Port(((CPU) component).getSocket(), Type.CPU_SOCKET));
                break;

            // GPU
            case 4:
                requiredPorts.add(new Port("Expansion", Type.EXPANSION, ((GPU) component).getExpansionSlots()));
                requiredPorts.add(new Port("PCIe_x16", Type.PCIe_x16));
                break;

            // Memory
            case 5:
                requiredPorts.add(new Port(((Memory) component).getType(), Type.RAM_SLOT, ((Memory) component).getModules()));
                break;

            // Cooler : the socket matching is checked by the Build object

            // Storage
            case 7:
                String form = ((Storage) component).getDriveFormFactor();
                if (form.equals("M.2")) {
                    requiredPorts.add(new Port("M2", Type.M_2));
                }
                else {
                    requiredPorts.add(new Port(((Storage) component).getDriveFormFactor(), Type.DRIVE_FORM_FACTOR));
                    requiredPorts.add(new Port("SATA", Type.SATA));
                }
                break;
        }

        return requiredPorts;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getCount() {
        return count;
    }
    public Type getType() {
        return type;
    }
    public void setType(Type type) {
        this.type = type;
    }
    public void setCount(int count) {
        this.count = count;
    }
}

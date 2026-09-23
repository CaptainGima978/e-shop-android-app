package gr.softeng.team16.domain;
import java.util.HashSet;

public class Component extends Product {

    private String manufacturer;
    private String img;
    protected HashSet<Port> providedPorts;
    protected HashSet<Port> requiredPorts;


    public Component(int id, String name, String manufacturer, double price, String img) {
        super(id, name, price);
        this.manufacturer = manufacturer;
        this.img = img;
    }

    public Component() {}

    public String getManufacturer() {
        return manufacturer;
    }
    public String getImg() {
        return img;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }
    public void setImg(String img) {
        this.img = img;
    }
}

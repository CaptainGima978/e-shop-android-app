package gr.softeng.team16.domain;

import java.util.Objects;

public class Product {

    private int id;
    private String name;
    private double price;
    private String img;

    public Product(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public Product() {}

    //copy constructor
    public Product(Product original) {
        this.id = original.id;
        this.name = original.name;
        this.price = original.price;
        this.img = original.img;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id == product.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public double getPrice() {
        return price;
    }
    public String getImg() {
        return img;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public void setImg(String img) {
        this.img = img;
    }
}

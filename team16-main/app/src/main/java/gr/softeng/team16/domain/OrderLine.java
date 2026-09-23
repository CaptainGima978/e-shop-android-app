package gr.softeng.team16.domain;

import java.io.Serializable;
import java.util.Objects;

public class OrderLine implements Serializable {

    private int quantity;
    private Product product;


    public OrderLine() {
    }

    public OrderLine(Product product){
        this.quantity = 1;
        this.product = product;
    }
    
    public OrderLine(int quantity, Product product){
        this.quantity = quantity;
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        OrderLine orderLine = (OrderLine) o;
        return Objects.equals(product, orderLine.product);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(product);
    }
}

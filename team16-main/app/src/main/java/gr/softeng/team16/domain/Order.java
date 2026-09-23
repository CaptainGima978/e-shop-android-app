package gr.softeng.team16.domain;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Order implements Serializable {

    private static int nextOrderId = 67;
    private int orderId;
    private int numOfProducts;
    private double totalPrice;
    private String status;
    private List<OrderLine> lines;
    private CustomerAccount customerAccount;
    private Address address;
    private String firebaseKey; // Added to track which order to delete
    private final PaymentService paymentService;


    public Order() {
        this.paymentService = new PaymentService(this);
    }

    public Order(int numOfProducts, double totalPrice) {
        this.orderId = nextOrderId++;
        this.status = "PENDING ORDER";
        this.numOfProducts = numOfProducts;
        this.totalPrice = totalPrice;
        this.paymentService = new PaymentService(this);
    }

    public Order(List<OrderLine> lines, CustomerAccount customerAccount, Address address) {
        this.orderId = nextOrderId++;
        this.status = "PENDING PAYMENT";
        this.lines = lines;
        this.customerAccount = customerAccount;
        this.address = address;

        this.totalPrice = 0.0;
        this.numOfProducts = 0;
        if (lines != null) {
            for (OrderLine line : lines) {
                this.numOfProducts += line.getQuantity();
                this.totalPrice += line.getProduct().getPrice() * line.getQuantity();
            }
        }
        this.paymentService = new PaymentService(this);
    }

    public void processPayment(Card card) {
        boolean paymentSuccessful = this.paymentService.processPayment(card);
        if (paymentSuccessful) {
            this.status = "PENDING ORDER";
        } else {
            this.status = "PAYMENT FAILED";
        }
    }

    @Exclude
    public PaymentService getPaymentService() {
        return new PaymentService(this);
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<OrderLine> getLines() {
        return lines;
    }

    public void setLines(List<OrderLine> lines) {
        this.lines = new ArrayList<>(lines);
        this.numOfProducts = 0;
        this.totalPrice = 0.0;
        for (OrderLine line : lines) {
            this.numOfProducts += line.getQuantity();
            this.totalPrice += line.getProduct().getPrice() * line.getQuantity();
        }
    }

    public int getNumOfProducts() {
        return numOfProducts;
    }

    public void setNumOfProducts(int numOfProducts) {
        this.numOfProducts = numOfProducts;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public CustomerAccount getCustomerAccount() {
        return customerAccount;
    }

    public void setCustomerAccount(CustomerAccount customerAccount) {
        this.customerAccount = customerAccount;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getFirebaseKey() {
        return firebaseKey;
    }

    public void setFirebaseKey(String firebaseKey) {
        this.firebaseKey = firebaseKey;
    }
}

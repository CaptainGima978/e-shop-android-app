package gr.softeng.team16.domain;
import java.io.Serializable;
import  java.util.ArrayList;
import  java.util.HashSet;
import  java.util.List;

public class CustomerAccount implements Serializable {


    private String name;
    private String username;
    private String email;
    private Cart cart;
    private Address address;
    private List<Card> cardList;
    private List<Order> orders;

    protected HashSet<Customer_Build> Customer_Builds_liked;
    protected HashSet<Customer_Build> Customer_Builds_saved;
    public CustomerAccount(String name, String username, String email, Address address){

        this.name = name;
        this.username = username;
        this.email = email;
        this.cart = new Cart();
        this.address = address;
        this.cardList = new ArrayList<>();
        this.orders = new ArrayList<>();
    }

    public CustomerAccount() {
        this.cart = new Cart();
        this.cardList = new ArrayList<>();
        this.orders = new ArrayList<>();
    }

    /** Add an order to the customer's order history
     *
     * @param order The order to be added
     */
    public void addOrder(Order order){
        this.orders.add(order);
    }

    /** Add a card to the customer's saved cards
     *
     * @param card The card to be added
     */
    public boolean addCard(Card card){
        for (Card c : this.cardList){
            if(c.equals(card)){
                return false;
            }
        }
        this.cardList.add(card);
        return true;
    }

    /** Remove a card from the customer's saved cards
     *
     * @param card The card to be removed
     */
    public void removeCard(Card card){
        this.cardList.remove(card);
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<Card> getCardList() {
        return cardList;
    }

    public void setCardList(List<Card> cardList) {
        this.cardList = cardList;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public void addCustomer_Build_liked(Customer_Build build){
        if (build.isPublished()) {
            Customer_Builds_liked.add(build);
        }
    }

    public void addCustomer_Build_saved(Customer_Build build){
        if (build.isPublished()) {
            Customer_Builds_saved.add(build);
        }
    }


    public void removeCustomer_Build_liked(Customer_Build build){
        if (!Customer_Builds_liked.isEmpty()) {
            Customer_Builds_liked.remove(build);
        }
    }

    public void removeCustomer_Build_saved(Customer_Build build){
        if (!Customer_Builds_saved.isEmpty()){
        Customer_Builds_saved.remove(build);
        }
   }
}

package gr.softeng.team16.domain;

import java.time.LocalDate;

public class PaymentService {

    private Order order;
    private double amount;
    private Card card;

    public PaymentService(Order order) {
        this.order = order;
        this.amount = order.getTotalPrice();
        this.card = null;
    }

    /**
     * Checks if a card expiration date (MM/YY) is in the past.
     * @param expiryDate The date string in MM/YY format.
     * @return True if the card is expired or format is invalid, false otherwise.
     */
    public static boolean isCardExpired(String expiryDate) {
        if (expiryDate == null || !expiryDate.matches("^(0[1-9]|1[0-2])/(\\d{2})$")) {
            return true;
        }

        try {
            String[] parts = expiryDate.split("/");
            int expMonth = Integer.parseInt(parts[0]);
            int expYear = Integer.parseInt(parts[1]) + 2000;

            LocalDate now = LocalDate.now();
            int currentMonth = now.getMonthValue();
            int currentYear = now.getYear();

            return expYear < currentYear || (expYear == currentYear && expMonth < currentMonth);
        } catch (Exception e) {
            return true;
        }
    }

    public boolean processPayment(Card card) {
        this.card = card;
        if (card == null || isCardExpired(card.getDate())) {
            return false;
        }

        // 5% chance the payment fails
        return Math.random() >= 0.05;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}

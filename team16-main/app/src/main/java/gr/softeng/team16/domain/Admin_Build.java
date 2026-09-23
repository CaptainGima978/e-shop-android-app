package gr.softeng.team16.domain;

public class Admin_Build extends Build {
    private double originalPrice;
    private double discountPrice;
    private boolean onDiscount;

    public Admin_Build(String name) {
        super(name);
        this.originalPrice = getPrice();
        this.discountPrice = originalPrice;
        this.onDiscount = false;
    }

    public Admin_Build(Build build) {
        super(build);
        this.originalPrice = getPrice();
    }

    public void updateOriginalPrice() {
        this.originalPrice = getPrice();
    }

    @Override
    public double getFinalPrice() {
        if (onDiscount){
            return discountPrice;
        } else {
            return originalPrice;
        }
    }

    public double getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(double discountPrice) {
        this.discountPrice = discountPrice;
    }

    public void setOnDiscount() {
        onDiscount = true;
    }

    public void setOffDiscount() {
        onDiscount = false;
    }

    public boolean isOnDiscount() {
        return onDiscount;
    }

}


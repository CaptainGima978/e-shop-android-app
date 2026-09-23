package gr.softeng.team16.domain;

public class Customer_Build extends Build {
    private boolean published;
    private int likes;
    private CustomerAccount customerAccount;

    public Customer_Build(String name) {
        super(name);
        this.published= false;
        this.likes = 0;
        this.customerAccount = null;
    }

    public Customer_Build(String name, CustomerAccount customerAccount) {
        super(name);
        this.published= false;
        this.likes = 0;
        this.customerAccount = customerAccount;
    }

    public void setCustomerAccount(CustomerAccount customerAccount) {
        this.customerAccount = customerAccount;
    }

    public CustomerAccount getCustomerAccount() {
        return customerAccount;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public boolean isPublished() {
        return published;
    }

    public void publishBuild(){
        if (isCompleted() && customerAccount != null) {
            published = true;
        }
    }

    public void unpublishBuild(){
        published = false;
        likes = 0;
    }


    public void like() {
        if (published) likes++;
    }

    public int getLikes() {
        return likes;
    }
}

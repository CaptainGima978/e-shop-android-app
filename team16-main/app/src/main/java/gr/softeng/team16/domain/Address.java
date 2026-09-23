package gr.softeng.team16.domain;

import java.io.Serializable;

public class Address implements Serializable {

    private String zipcode;
    private String street;
    private int number;
    private String city;

    public Address(String city, String street, int number, String zipcode){
        this.city = city;
        this.street = street;
        this.number = number;
        this.zipcode = zipcode;
    }

    public Address() {}

    /**
     * Checks if city name contains only letters and spaces.
     */
    public static boolean checkCity_validity(String city) {
        return city != null && city.matches("^[a-zA-Z\\s]+$");
    }

    /**
     * Checks if zip code is exactly 5 digits.
     */
    public static boolean checkZipcode_validity(String zipcode) {
        return zipcode != null && zipcode.matches("^\\d{5}$");
    }

    /**
     * Checks if address matches: [Street Name] [Space] [1-3 Digits]
     */
    public static boolean checkAddress_validity(String fullAddress) {
        return fullAddress != null && fullAddress.matches("^[a-zA-Z\\s]+\\s\\d{1,3}$");
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}

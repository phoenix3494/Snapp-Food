package model;

import java.io.Serializable;
import java.util.ArrayList;

public class PreOrder implements Serializable {
    private int costumerID;
    private int cafeRestaurantID;
    private String cafeRestaurantName;
    private double price;
    private ArrayList<Item> items = new ArrayList<Item>();
    private int numberOfItems = 0;

    public PreOrder(int costumerID, int cafeRestaurantID, String cafeRestaurantName, Long price) {

        this.costumerID = costumerID;
        this.cafeRestaurantID = cafeRestaurantID;
        this.cafeRestaurantName = cafeRestaurantName;
        this.price = price;

    }

    public int getCostumerID() {
        return costumerID;
    }

    public void setCostumerID(int costumerID) {
        this.costumerID = costumerID;
    }

    public int getCafeRestaurantID() {
        return cafeRestaurantID;
    }

    public void setCafeRestaurantID(int cafeRestaurantID) {
        this.cafeRestaurantID = cafeRestaurantID;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    public String getCafeRestaurantName() {
        return cafeRestaurantName;
    }

    public void setCafeRestaurantName(String cafeRestaurantName) {
        this.cafeRestaurantName = cafeRestaurantName;
    }

    public int getNumberOfItems() {
        return numberOfItems;
    }

    public void setNumberOfItems(int numberOfItems) {
        this.numberOfItems = numberOfItems;
    }

}

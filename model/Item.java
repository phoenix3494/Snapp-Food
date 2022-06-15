package model;

import java.io.Serializable;

public class Item implements Serializable {
    private int foodCatagoryID;
    private int itemID;
    private int restaurantID;
    private String Item_name;
    private double price;
    public double overall = 0D;
    private long numberOfScorers;

    private String restaurantName;

    public Item(String item_name, double price, int catagoryID, int itemID, int restaurantID, String restaurantName) {
        Item_name = item_name;
        this.price = price;
        this.numberOfScorers = 0;
        this.foodCatagoryID = catagoryID;
        this.itemID = itemID;
        this.restaurantID = restaurantID;
        this.restaurantName = restaurantName;

    }

    public String getItem_name() {
        return Item_name;
    }

    public void setItem_name(String item_name) {
        Item_name = item_name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getRating() {
        return overall;
    }

    public void setRating(double rating) {
        this.overall = rating;
    }

    public long getNumberOfScorers() {
        return numberOfScorers;
    }

    public void setNumberOfScorers(long numberOfScorers) {
        this.numberOfScorers = numberOfScorers;
    }

    public int getFoodCatagoryID() {
        return foodCatagoryID;
    }

    public void setFoodCatagoryID(int foodCatagoryID) {
        this.foodCatagoryID = foodCatagoryID;
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public int getRestaurantID() {
        return restaurantID;
    }

    public void setRestaurantID(int restaurantID) {
        this.restaurantID = restaurantID;
    }

    public void addScore(double score) {
        double num = numberOfScorers * overall;
        num = num + score;
        numberOfScorers = numberOfScorers + 1;
        setRating(num / numberOfScorers);
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }
}

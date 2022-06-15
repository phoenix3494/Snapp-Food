package model;

import java.io.Serializable;

public class OrderItem implements Serializable {
    private double Rating;
    private double price;
    private int itemID;
    private int foodCatagoryID;
    private int restaurantId;
    private String Item_name;

    public OrderItem(double price, int itemID, int foodCatagoryID, int restaurantId, String item_name) {
        this.Rating = 0;
        this.price = price;
        this.itemID = itemID;
        this.foodCatagoryID = foodCatagoryID;
        this.restaurantId = restaurantId;
        Item_name = item_name;
    }

    public double getRating() {
        return Rating;
    }

    public void setRating(double rating) {
        this.Rating = rating;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public int getFoodCatagoryID() {
        return foodCatagoryID;
    }

    public void setFoodCatagoryID(int foodCatagoryID) {
        this.foodCatagoryID = foodCatagoryID;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getItem_name() {
        return Item_name;
    }

    public void setItem_name(String item_name) {
        Item_name = item_name;
    }

}

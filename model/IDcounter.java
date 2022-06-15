package model;

import java.io.Serializable;

public class IDcounter implements Serializable {
    private int costumerID = 1;
    private int deliveryID = 1;
    private int orderID = 1;
    private int cafeRestaurantID = 1;
    private int itemsID = 1;
    private int foodcatagoryID = 1;

    public void addFoodCatagory()
    {
        this.foodcatagoryID = foodcatagoryID +1;
    }
    public void addItem()
    {
        this.itemsID = itemsID + 1;
    }

    public int getFoodcatagoryID() {
        return foodcatagoryID;
    }

    public void setFoodcatagoryID(int foodcatagoryID) {
        this.foodcatagoryID = foodcatagoryID;
    }
    public int getItemsID() {
        return itemsID;
    }

    public void setItemsID(int itemsID) {
        this.itemsID = itemsID;
    }


    public void AddCostumer()
    {
        this.costumerID = this.costumerID + 1;
    }
    public void AddDelivery()
    {
        this.deliveryID = this.deliveryID + 1;
    }
    public void AddOrder()
    {
        this.orderID = this.orderID + 1;
    }
    public void AddCafeRestaurant()
    {
        this.cafeRestaurantID = this.cafeRestaurantID + 1;
    }
    public int getCostumerID() {
        return costumerID;
    }
    public int getDeliveryID() {
        return deliveryID;
    }
    public int getOrderID() {
        return orderID;
    }
    public int getCafeRestaurantID() {
        return cafeRestaurantID;
    }

}

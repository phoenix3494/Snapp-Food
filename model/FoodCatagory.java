package model;

import java.io.Serializable;
import java.util.ArrayList;

public class FoodCatagory implements Serializable {
    private int foodCatagoryID;
    private String foodCatagoryName;
    private ArrayList<Item> items = new ArrayList<Item>();


    public FoodCatagory(int id, String foodCatagoryName) {
        foodCatagoryID = id;
        this.foodCatagoryName = foodCatagoryName;
    }

    public int getID() {
        return foodCatagoryID;
    }

    public void setID(int ID) {
        this.foodCatagoryID = ID;
    }

    public void setFoodCatagoryName(String foodCatagoryName) {
        this.foodCatagoryName = foodCatagoryName;
    }

    public String getFoodCatagoryName() {
        return foodCatagoryName;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }




}

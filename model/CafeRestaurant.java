package model;

import java.io.Serializable;
import java.util.ArrayList;

public class CafeRestaurant extends Place implements Serializable {
    CAFEORRESTAURANT type;
    private int ID;
    private String name;
    private ArrayList<FoodCatagory> foods = new ArrayList<FoodCatagory>();

    public CafeRestaurant(CAFEORRESTAURANT T, int id, String placeName, String address, String name) {
        super(placeName, address);
        this.name = name;
        this.type = T;
        this.ID = id;

    }

    public CAFEORRESTAURANT getType() {
        return type;
    }

    public void setType(CAFEORRESTAURANT type) {
        this.type = type;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<FoodCatagory> getFoods() {
        return foods;
    }

    public void setFoods(ArrayList<FoodCatagory> foods) {
        this.foods = foods;
    }

}

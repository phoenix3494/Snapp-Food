package model;

import java.io.Serializable;
import java.util.ArrayList;


public class Costumer extends Person implements Serializable {
    private int ID;
    private double money;
    private ArrayList<CafeRestaurant> CostumerRestaurants = new ArrayList<CafeRestaurant>();
    private ArrayList<Order> Orders = new ArrayList<Order>();
    private ArrayList<PreOrder> preOrders = new ArrayList<PreOrder>();
    private ArrayList<DiscountCode> CostumerDiscounts = new ArrayList<DiscountCode>();

    public Costumer(int ID,String name, String lastName, String username, String password, String phoneNumber) {
        super(name, lastName, username, password, phoneNumber);
        this.ID = ID;
        this.money = 0;
    }
    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }
    public  void chargeMoney(double money)
    {
        this.money = this.money + money;
    }


    public ArrayList<CafeRestaurant> getCostumerRestaurants() {
        return CostumerRestaurants;
    }

    public void setCostumerRestaurants(ArrayList<CafeRestaurant> costumerRestaurants) {
        CostumerRestaurants = costumerRestaurants;
    }

    public ArrayList<Order> getOrders() {
        return Orders;
    }

    public void setOrders(ArrayList<Order> costumerOrders) {
        Orders = costumerOrders;
    }

    public ArrayList<DiscountCode> getCostumerDiscounts() {
        return CostumerDiscounts;
    }

    public void setCostumerDiscounts(ArrayList<DiscountCode> costumerDiscounts) {
        CostumerDiscounts = costumerDiscounts;
    }

    public int getID() {
        return ID;
    }

    public void setID(int costumerID) {
        ID = costumerID;
    }

    public ArrayList<PreOrder> getPreOrders() {
        return preOrders;
    }

    public void setPreOrders(ArrayList<PreOrder> preOrders) {
        this.preOrders = preOrders;
    }
}

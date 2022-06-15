package model;

import java.io.Serializable;
import java.util.ArrayList;

public class Delivery extends Person implements Serializable {

    private  int ID;
    private ArrayList<Order> orders = new ArrayList<Order>();
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }

    public void setOrders(ArrayList<Order> orders) {
        this.orders = orders;
    }

    public Delivery(int id,String name, String lastName, String username, String password, String phoneNumber) {
        super(name, lastName, username, password, phoneNumber);
        this.ID = id;
    }
}

package controller;

import model.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class SetInFile {

    public static void setCounter(IDcounter counter) {
        try {
            FileOutputStream fos = new FileOutputStream("counter.txt");
            ObjectOutputStream oos = new ObjectOutputStream(fos);// add contact to arraylist and table
            oos.writeObject(counter);// write updated array list to contacts file
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("error");
        }
    }

    public static void setOrders(ArrayList<Order> orderss) {
        try {
            FileOutputStream fos = new FileOutputStream("orders.txt");
            ObjectOutputStream oos = new ObjectOutputStream(fos);// add contact to arraylist and table
            oos.writeObject(orderss);// write updated array list to contacts file
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("error");
        }
    }

    public static void setinvitations(ArrayList<Invitation> invitations) {
        try {
            FileOutputStream fos = new FileOutputStream("invitations.txt");
            ObjectOutputStream oos = new ObjectOutputStream(fos);// add contact to arraylist and table
            oos.writeObject(invitations);// write updated array list to contacts file
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("error");
        }
    }

    public static void setCafeRestaurants(ArrayList<CafeRestaurant> cafeRestaurants) {
        try {
            FileOutputStream fos = new FileOutputStream("CafeRestaurants.txt");
            ObjectOutputStream oos = new ObjectOutputStream(fos);// add contact to arraylist and table
            oos.writeObject(cafeRestaurants);// write updated array list to contacts file
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("error");
        }
    }

    public static void setDelivery(ArrayList<Delivery> delivery) {
        try {
            FileOutputStream fos = new FileOutputStream("deliveries.txt");
            ObjectOutputStream oos = new ObjectOutputStream(fos);// add contact to arraylist and table
            oos.writeObject(delivery);// write updated array list to contacts file
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("error");
        }
    }

    public static void setcostumers(ArrayList<Costumer> costumers) {
        try {
            FileOutputStream fos = new FileOutputStream("costumers.txt");
            ObjectOutputStream oos = new ObjectOutputStream(fos);// add contact to arraylist and table
            oos.writeObject(costumers);// write updated array list to contacts file
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("error");
        }
    }

}

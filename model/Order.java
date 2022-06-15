package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Order implements Serializable {
    private int orderID;
    private int costumerID;
    private String SenderAddress;
    private String ReceiverAddress;
    private String costumerName;
    Date date = new Date();

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    private String costumerFname;
    private String costumerPhoneNumber;
    private int numberOfItems;
    private String RestaurantName;
    private OrderStatus status;
    private ArrayList<OrderItem> items = new ArrayList<OrderItem>();
    private Double price;

    public Order(int orderID, int costumerID, String senderAddress, String receiverAddress, String restaurantName,
            ArrayList<OrderItem> items, double price, String costumerName, String costumerFname,
            String costumerPhoneNumber) {
        this.orderID = orderID;
        this.costumerID = costumerID;
        SenderAddress = senderAddress;
        ReceiverAddress = receiverAddress;
        RestaurantName = restaurantName;
        this.status = OrderStatus.NotAccepted;
        this.items = items;
        this.numberOfItems = this.items.size();
        this.price = price;
        this.costumerFname = costumerFname;
        this.costumerName = costumerName;
        this.costumerPhoneNumber = costumerPhoneNumber;
    }

    public String getCostumerName() {
        return costumerName;
    }

    public void setCostumerName(String costumerName) {
        this.costumerName = costumerName;
    }

    public String getCostumerFname() {
        return costumerFname;
    }

    public void setCostumerFname(String costumerFname) {
        this.costumerFname = costumerFname;
    }

    public String getCostumerPhoneNumber() {
        return costumerPhoneNumber;
    }

    public void setCostumerPhoneNumber(String costumerPhoneNumber) {
        this.costumerPhoneNumber = costumerPhoneNumber;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getCostumerID() {
        return costumerID;
    }

    public void setCostumerID(int costumerID) {
        this.costumerID = costumerID;
    }

    public String getSenderAddress() {
        return SenderAddress;
    }

    public void setSenderAddress(String senderAddress) {
        SenderAddress = senderAddress;
    }

    public String getReceiverAddress() {
        return ReceiverAddress;
    }

    public void setReceiverAddress(String receiverAddress) {
        ReceiverAddress = receiverAddress;
    }

    public int getNumberOfItems() {
        return numberOfItems;
    }

    public void setNumberOfItems(int numberOfItems) {
        this.numberOfItems = numberOfItems;
    }

    public String getRestaurantName() {
        return RestaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        RestaurantName = restaurantName;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public ArrayList<OrderItem> getItems() {
        return items;
    }

    public void setItems(ArrayList<OrderItem> items) {
        this.items = items;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}

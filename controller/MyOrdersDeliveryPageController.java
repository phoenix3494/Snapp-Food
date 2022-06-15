package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Costumer;
import model.Delivery;
import model.Order;
import model.OrderStatus;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;

public class MyOrdersDeliveryPageController {
    Stage stage;
    private Delivery delivery;
    private ArrayList<Order> orders = new ArrayList<Order>();
    private ArrayList<Costumer> costumers = new ArrayList<Costumer>();
    private ArrayList<Delivery> deliveries = new ArrayList<Delivery>();
    @FXML
    private Label errorLBL;

    @FXML
    private TableView<Order> orderTBL;

    TableColumn<Order, Integer> Order_ID;
    TableColumn<Order, Integer> numberOfItems;
    TableColumn<Order, String> senderAddress;
    TableColumn<Order, String> receiverAddress;
    TableColumn<Order, Double> price;
    TableColumn<Order, Date> date;
    TableColumn<Order, String> receiverName;
    TableColumn<Order, String> receiverFamilyname;
    TableColumn<Order, String> receiverPhoneNumber;
    TableColumn<Order, String> restaurantName;

    public void initfunction(Stage stage, Delivery delivery, ArrayList<Order> orders, ArrayList<Delivery> deliveries,
            ArrayList<Costumer> costumers) {
        this.stage = stage;
        this.delivery = delivery;
        this.orders = orders;
        this.deliveries = deliveries;
        this.costumers = costumers;
        this.Order_ID = new TableColumn<>("ID");
        this.numberOfItems = new TableColumn<>("Number Of Items");
        this.senderAddress = new TableColumn<>("Sender Address");
        this.receiverAddress = new TableColumn<>("Receiver Address");
        this.price = new TableColumn<>("price");
        this.date = new TableColumn<>("date");
        receiverName = new TableColumn<>("Receiver Name");
        receiverFamilyname = new TableColumn<>("Receiver FamilyName");
        receiverPhoneNumber = new TableColumn<>("Receiver PhoneNumber");
        restaurantName = new TableColumn<>("Restaurant Name");
        this.Order_ID.setCellValueFactory(new PropertyValueFactory<>("orderID"));
        this.numberOfItems.setCellValueFactory(new PropertyValueFactory<>("numberOfItems"));
        this.senderAddress.setCellValueFactory(new PropertyValueFactory<>("SenderAddress"));
        this.price.setCellValueFactory(new PropertyValueFactory<>("price"));
        this.receiverAddress.setCellValueFactory(new PropertyValueFactory<>("ReceiverAddress"));
        this.date.setCellValueFactory(new PropertyValueFactory<>("date"));
        receiverName.setCellValueFactory(new PropertyValueFactory<>("costumerName"));
        receiverFamilyname.setCellValueFactory(new PropertyValueFactory<>("costumerFname"));
        receiverPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("costumerPhoneNumber"));
        restaurantName.setCellValueFactory(new PropertyValueFactory<>("RestaurantName"));
        orderTBL.getColumns().addAll(Order_ID, numberOfItems, date, senderAddress, restaurantName, receiverAddress,
                receiverName, receiverFamilyname, receiverPhoneNumber, price);
        orderTBL.getItems().addAll(delivery.getOrders());

    }

    @FXML
    void back(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("..\\view\\deliverymainpage.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        deliveryMainPageController controller = loader.getController();
        controller.initfunction(this.stage, delivery, orders, deliveries, costumers);

        this.stage.setScene(new Scene(loader.getRoot()));

    }

    @FXML
    void deliver(ActionEvent event) {
        Order selectedOrder = orderTBL.getSelectionModel().getSelectedItem();
        if (selectedOrder != null) {
            selectedOrder.setStatus(OrderStatus.Delivered);
            Delivered(selectedOrder);
            delivery.getOrders().remove(selectedOrder);
            orderTBL.getItems().remove(selectedOrder);
            errorLBL.setText("Order Delivered");
            SetInFile.setOrders(orders);
            SetInFile.setDelivery(deliveries);
            SetInFile.setcostumers(costumers);
        }

    }

    private void Delivered(Order selectedOrder) {
        int costumerID = selectedOrder.getCostumerID();
        int OrderID = selectedOrder.getOrderID();
        ArrayList<Order> costumerOrders = null;
        for (int i = 0; i < costumers.size(); i++) {
            if (costumerID == costumers.get(i).getID()) {
                costumerOrders = costumers.get(i).getOrders();
            }
        }
        for (int i = 0; i < costumerOrders.size(); i++) {
            if (costumerOrders.get(i).getOrderID() == OrderID) {
                costumerOrders.get(i).setStatus(OrderStatus.Delivered);
            }
        }
        for (int i = 0; i < orders.size(); i++) {
            if (orders.get(i).getOrderID() == OrderID) {
                orders.get(i).setStatus(OrderStatus.Delivered);
            }
        }
    }

}

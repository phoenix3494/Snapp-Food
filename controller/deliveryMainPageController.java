package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.CafeRestaurant;
import model.Costumer;
import model.Delivery;
import model.Order;

import java.io.IOException;
import java.util.ArrayList;

public class deliveryMainPageController {
    private Stage stage;
    private Delivery delivery;
    private ArrayList<Order> orders = new ArrayList<Order>();
    private  ArrayList<Costumer> costumers = new ArrayList<Costumer>();
    private  ArrayList<Delivery> deliveries = new ArrayList<Delivery>();

    public void initfunction(Stage stage , Delivery delivery ,ArrayList<Order> orders,ArrayList<Delivery> deliveries,ArrayList<Costumer> costumers )
    {
        this.stage = stage;
        this.delivery = delivery;
        this.orders = orders;
        this.deliveries =deliveries;
        this.costumers = costumers;
    }

    @FXML
    void allorders(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("..\\view\\allordersdeliveypage.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        AllOrdersDeliveryPageController controller = loader.getController();
        controller.initfunction(this.stage,delivery,orders,deliveries,costumers);

        this.stage.setScene(new Scene(loader.getRoot()));

    }

    @FXML
    void exit(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("..\\view\\startpage.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        StartPageController controller = loader.getController();
        controller.initfunction(this.stage);

        this.stage.setScene(new Scene(loader.getRoot()));
    }

    @FXML
    void myorders(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("..\\view\\myordersdeliverypage.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        MyOrdersDeliveryPageController controller = loader.getController();
        controller.initfunction(this.stage,delivery,orders,deliveries,costumers);
        this.stage.setScene(new Scene(loader.getRoot()));
    }
}

package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.CafeRestaurant;
import model.Costumer;
import model.Delivery;

import java.io.IOException;
import java.util.ArrayList;

public class AdminMainPageController {
    Stage stage;
    private ArrayList<CafeRestaurant> cafeRestaurants = new ArrayList<CafeRestaurant>();
    private ArrayList<Delivery> deliveries = new ArrayList<Delivery>();
    private ArrayList<Costumer> costumers = new ArrayList<Costumer>();

    public void initfunction(Stage stage , ArrayList<CafeRestaurant> cafeRestaurants ,ArrayList<Delivery> deliveries,ArrayList<Costumer> costumers )
    {
        this.stage = stage;
        this.deliveries = deliveries;
        this.costumers = costumers;
        this.cafeRestaurants = cafeRestaurants;
    }


    @FXML
    void adddelivery(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("..\\view\\adddeliverypage.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        AddDeliveryPageController controller = loader.getController();
        controller.initfunction(stage,deliveries,costumers,cafeRestaurants);

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
    void resCafe(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("..\\view\\adminrestaurantspage.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        AddRestaurantPageController controller = loader.getController();
        controller.initfunction(stage,cafeRestaurants,deliveries,costumers);

        this.stage.setScene(new Scene(loader.getRoot()));


    }
}

package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.*;
import java.io.IOException;
import java.util.ArrayList;

public class CostumerCartPageController {
    private Stage stage;
    private Costumer costumer;
    private ArrayList<Order> orders = new ArrayList<Order>();
    private ArrayList<CafeRestaurant> cafeRestaurants = new ArrayList<CafeRestaurant>();
    private ArrayList<Costumer> costumers = new ArrayList<Costumer>();
    @FXML
    private TableView<PreOrder> preOrderTBL;
    private TableColumn<PreOrder, String> restaurantName;
    private TableColumn<PreOrder, Double> Price;
    private TableColumn<PreOrder, Integer> numberOfItems;

    public void initfunction(Stage stage, Costumer costumer, ArrayList<Order> orders,
            ArrayList<CafeRestaurant> cafeRestaurants, ArrayList<Costumer> costumers) {
        this.stage = stage;
        this.costumer = costumer;
        this.orders = orders;
        this.cafeRestaurants = cafeRestaurants;
        this.costumers = costumers;
        restaurantName = new TableColumn<>("Restaurant's Name");
        Price = new TableColumn<>("Price");
        numberOfItems = new TableColumn<>("Number Of Items");
        restaurantName.setCellValueFactory(new PropertyValueFactory<>("cafeRestaurantName"));
        Price.setCellValueFactory(new PropertyValueFactory<>("price"));
        numberOfItems.setCellValueFactory(new PropertyValueFactory<>("numberOfItems"));
        preOrderTBL.getColumns().addAll(restaurantName, numberOfItems, Price);
        preOrderTBL.getItems().addAll(costumer.getPreOrders());
    }

    @FXML
    void back(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("..\\view\\costumerrestaurantpage.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        CostumerMainPageController controller = loader.getController();
        controller.initfunction(stage, costumer, orders, cafeRestaurants, costumers);

        this.stage.setScene(new Scene(loader.getRoot()));

    }

    @FXML
    void finalize(ActionEvent event) {
        PreOrder selectedPreOrder = preOrderTBL.getSelectionModel().getSelectedItem();
        if (selectedPreOrder != null) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("..\\view\\finalizePayment.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            CostumerFinalizePaymentPageController controller = loader.getController();
            controller.initfunction(stage, costumer, orders, cafeRestaurants, costumers, selectedPreOrder);
            this.stage.setScene(new Scene(loader.getRoot()));
        }

    }

    @FXML
    void remove(ActionEvent event) {
        PreOrder selectedPreOrder = preOrderTBL.getSelectionModel().getSelectedItem();
        if (selectedPreOrder != null) {
            costumer.getPreOrders().remove(selectedPreOrder);
            preOrderTBL.getItems().remove(selectedPreOrder);
            SetInFile.setcostumers(costumers);
        }
    }
}

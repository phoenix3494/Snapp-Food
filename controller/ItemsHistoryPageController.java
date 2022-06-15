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

public class ItemsHistoryPageController {
    private Stage stage;
    private Costumer costumer;
    private ArrayList<Order> orders = new ArrayList<Order>();
    private ArrayList<CafeRestaurant> cafeRestaurants = new ArrayList<CafeRestaurant>();
    private double rating;
    private double price;
    private int itemID;
    private int foodCatagoryID;
    private int restaurantId;
    private String Item_name;
    @FXML
    private TableView<OrderItem> itemsTBL;
    private TableColumn<OrderItem, String> itemsName;
    private TableColumn<OrderItem, Double> pricee;
    private TableColumn<OrderItem, Double> rate;
    private Order selectedOrder;

    private ArrayList<Costumer> costumers = new ArrayList<Costumer>();

    public void initfunction(Stage stage, Costumer costumer, ArrayList<Order> orders,
            ArrayList<CafeRestaurant> cafeRestaurants, ArrayList<Costumer> costumers, Order selectedOrder) {
        this.stage = stage;
        this.costumer = costumer;
        this.orders = orders;
        this.cafeRestaurants = cafeRestaurants;
        this.costumers = costumers;
        this.selectedOrder = selectedOrder;
        itemsName = new TableColumn<>("Item's Name");
        pricee = new TableColumn<>("Price");
        rate = new TableColumn<>("My Rating");
        itemsName.setCellValueFactory(new PropertyValueFactory<>("Item_name"));
        pricee.setCellValueFactory(new PropertyValueFactory<>("price"));
        rate.setCellValueFactory(new PropertyValueFactory<>("rating"));
        itemsTBL.getColumns().addAll(itemsName, pricee, rate);
        itemsTBL.getItems().addAll(selectedOrder.getItems());
    }

    @FXML
    void back(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("..\\view\\history.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        HistoryPageController controller = loader.getController();
        controller.initfunction(stage, costumer, orders, cafeRestaurants, costumers);
        this.stage.setScene(new Scene(loader.getRoot()));
    }

}
